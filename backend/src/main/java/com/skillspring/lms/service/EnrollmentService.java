package com.skillspring.lms.service;

import com.skillspring.lms.model.Course;
import com.skillspring.lms.model.Enrollment;
import com.skillspring.lms.model.Payment;
import com.skillspring.lms.model.User;
import com.skillspring.lms.repository.CourseRepository;
import com.skillspring.lms.repository.EnrollmentRepository;
import com.skillspring.lms.repository.PaymentRepository;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class EnrollmentService {
  private final CourseRepository courseRepository;
  private final EnrollmentRepository enrollmentRepository;
  private final PaymentRepository paymentRepository;

  public EnrollmentService(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository,
                           PaymentRepository paymentRepository) {
    this.courseRepository = courseRepository;
    this.enrollmentRepository = enrollmentRepository;
    this.paymentRepository = paymentRepository;
  }

  public Map<String, Object> enroll(User user, Long courseId) {
    Course course = courseRepository.findById(courseId).orElse(null);

    if (course == null) {
      throw new ResponseStatusException(NOT_FOUND, "Course not found");
    }

    if (enrollmentRepository.findByCourseIdAndEmail(courseId, user.getEmail()).isPresent()) {
      throw new ResponseStatusException(BAD_REQUEST, "You are already enrolled in this course");
    }

    Enrollment enrollment = new Enrollment(
        null,
        courseId,
        user.getEmail(),
        user.getName(),
        Instant.now().toString(),
        "Active",
        0
    );
    Payment payment = paymentRepository.save(new Payment(
        "PAY-" + System.currentTimeMillis(),
        courseId,
        user.getEmail(),
        course.getPrice(),
        "Razorpay Demo",
        "Paid",
        Instant.now().toString()
    ));
    Enrollment saved = enrollmentRepository.save(enrollment);
    return Map.of("enrollment", saved, "payment", payment);
  }

  public List<Enrollment> getMyEnrollments(User user) {
    return enrollmentRepository.findByEmailOrderByIdAsc(user.getEmail());
  }

  public Enrollment updateProgress(User user, Long courseId, int progress) {
    Enrollment enrollment = enrollmentRepository.findByCourseIdAndEmail(courseId, user.getEmail()).orElse(null);

    if (enrollment == null) {
      throw new ResponseStatusException(NOT_FOUND, "Enrollment not found");
    }

    enrollment.setProgress(progress);
    return enrollmentRepository.save(enrollment);
  }

  public List<Payment> getPayments() {
    return paymentRepository.findAllByOrderByPaidAtAsc();
  }

  public List<Enrollment> getAllEnrollments() {
    return enrollmentRepository.findAllByOrderByIdAsc();
  }
}
