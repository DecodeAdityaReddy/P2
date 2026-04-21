package com.skillspring.lms.service;

import com.skillspring.lms.model.Course;
import com.skillspring.lms.model.Enrollment;
import com.skillspring.lms.model.Payment;
import com.skillspring.lms.model.User;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class EnrollmentService {
  private final DataStore dataStore;

  public EnrollmentService(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  public Map<String, Object> enroll(User user, Long courseId) {
    Course course = dataStore.findCourse(courseId);

    if (course == null) {
      throw new ResponseStatusException(NOT_FOUND, "Course not found");
    }

    if (dataStore.findEnrollment(courseId, user.getEmail()) != null) {
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
    Payment payment = dataStore.savePayment(courseId, user.getEmail(), course.getPrice());
    Enrollment saved = dataStore.saveEnrollment(enrollment);
    return Map.of("enrollment", saved, "payment", payment);
  }

  public List<Enrollment> getMyEnrollments(User user) {
    return dataStore.findEnrollmentsByEmail(user.getEmail());
  }

  public Enrollment updateProgress(User user, Long courseId, int progress) {
    Enrollment enrollment = dataStore.updateProgress(courseId, user.getEmail(), progress);

    if (enrollment == null) {
      throw new ResponseStatusException(NOT_FOUND, "Enrollment not found");
    }

    return enrollment;
  }

  public List<Payment> getPayments() {
    return dataStore.getPayments();
  }

  public List<Enrollment> getAllEnrollments() {
    return dataStore.getAllEnrollments();
  }
}
