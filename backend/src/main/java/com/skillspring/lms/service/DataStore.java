package com.skillspring.lms.service;

import com.skillspring.lms.model.Announcement;
import com.skillspring.lms.model.Course;
import com.skillspring.lms.model.Enrollment;
import com.skillspring.lms.model.Payment;
import com.skillspring.lms.model.Role;
import com.skillspring.lms.model.User;
import jakarta.annotation.PostConstruct;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DataStore {
  private final PasswordEncoder passwordEncoder;
  private final AtomicLong userIds = new AtomicLong(3);
  private final AtomicLong courseIds = new AtomicLong(4);
  private final AtomicLong enrollmentIds = new AtomicLong();

  private final Map<String, User> usersByEmail = new ConcurrentHashMap<>();
  private final Map<Long, Course> courses = new ConcurrentHashMap<>();
  private final Map<Long, Enrollment> enrollments = new ConcurrentHashMap<>();
  private final List<Payment> payments = new ArrayList<>();
  private final List<Announcement> announcements = new ArrayList<>();

  public DataStore(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @PostConstruct
  void seed() {
    usersByEmail.put("student@lms.com",
        new User(1L, "Aarav Student", "student@lms.com",
            passwordEncoder.encode("password"), Role.STUDENT));
    usersByEmail.put("instructor@lms.com",
        new User(2L, "Ishita Instructor", "instructor@lms.com",
            passwordEncoder.encode("password"), Role.INSTRUCTOR));
    usersByEmail.put("admin@lms.com",
        new User(3L, "Neel Admin", "admin@lms.com",
            passwordEncoder.encode("password"), Role.ADMIN));

    courses.put(1L, new Course(1L, "Java for Backend Development", "Backend", "Intermediate",
        "Ishita Sharma", 1999, "8 weeks", 24, 4.8,
        "Build production-ready Java backend skills with APIs, exception handling, and layered architecture."));
    courses.put(2L, new Course(2L, "Spring Boot REST API Masterclass", "Backend", "Advanced",
        "Rohan Mehta", 2499, "10 weeks", 30, 4.9,
        "Design secure REST APIs with Spring Boot, JPA, validation, and real-world service patterns."));
    courses.put(3L, new Course(3L, "React.js Frontend Essentials", "Frontend", "Beginner",
        "Kavya Nair", 1499, "6 weeks", 18, 4.7,
        "Create responsive single-page applications with React components, routing, and state-driven UI."));
    courses.put(4L, new Course(4L, "JWT Authentication in Modern Apps", "Security", "Intermediate",
        "Neel Verma", 1799, "5 weeks", 16, 4.6,
        "Implement secure authentication flows using JWT, protected routes, token storage, and role checks."));

    announcements.add(new Announcement(1L, "New Java backend track available", "All users"));
    announcements.add(new Announcement(2L, "Weekend live mentor session on Spring Security", "Students"));
  }

  public User findUserByEmail(String email) {
    return usersByEmail.get(email);
  }

  public User saveUser(User user) {
    if (user.getId() == null) {
      user.setId(userIds.incrementAndGet());
    }
    usersByEmail.put(user.getEmail(), user);
    return user;
  }

  public List<Course> getCourses() {
    return courses.values().stream()
        .sorted(Comparator.comparing(Course::getId))
        .toList();
  }

  public Course findCourse(Long courseId) {
    return courses.get(courseId);
  }

  public Course saveCourse(Course course) {
    if (course.getId() == null) {
      course.setId(courseIds.incrementAndGet());
    }
    courses.put(course.getId(), course);
    return course;
  }

  public Enrollment saveEnrollment(Enrollment enrollment) {
    if (enrollment.getId() == null) {
      enrollment.setId(enrollmentIds.incrementAndGet());
    }
    enrollments.put(enrollment.getId(), enrollment);
    return enrollment;
  }

  public Enrollment findEnrollment(Long courseId, String email) {
    return enrollments.values().stream()
        .filter(item -> item.getCourseId().equals(courseId) && item.getEmail().equals(email))
        .findFirst()
        .orElse(null);
  }

  public List<Enrollment> findEnrollmentsByEmail(String email) {
    return enrollments.values().stream()
        .filter(item -> item.getEmail().equals(email))
        .sorted(Comparator.comparing(Enrollment::getId))
        .toList();
  }

  public List<Enrollment> getAllEnrollments() {
    return enrollments.values().stream()
        .sorted(Comparator.comparing(Enrollment::getId))
        .toList();
  }

  public Enrollment updateProgress(Long courseId, String email, int progress) {
    Enrollment enrollment = findEnrollment(courseId, email);
    if (enrollment == null) {
      return null;
    }
    enrollment.setProgress(progress);
    return enrollment;
  }

  public Payment savePayment(Long courseId, String email, Integer amount) {
    Payment payment = new Payment(
        "PAY-" + System.currentTimeMillis(),
        courseId,
        email,
        amount,
        "Razorpay Demo",
        "Paid",
        Instant.now().toString()
    );
    payments.add(payment);
    return payment;
  }

  public List<Payment> getPayments() {
    return payments.stream()
        .sorted(Comparator.comparing(Payment::getPaidAt))
        .toList();
  }

  public List<Announcement> getAnnouncements() {
    return List.copyOf(announcements);
  }
}
