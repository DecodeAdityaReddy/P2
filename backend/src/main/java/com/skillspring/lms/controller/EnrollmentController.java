package com.skillspring.lms.controller;

import com.skillspring.lms.dto.ProgressUpdateRequest;
import com.skillspring.lms.model.Announcement;
import com.skillspring.lms.model.Enrollment;
import com.skillspring.lms.model.Payment;
import com.skillspring.lms.model.User;
import com.skillspring.lms.service.AnnouncementService;
import com.skillspring.lms.service.EnrollmentService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EnrollmentController {
  private final EnrollmentService enrollmentService;
  private final AnnouncementService announcementService;

  public EnrollmentController(EnrollmentService enrollmentService, AnnouncementService announcementService) {
    this.enrollmentService = enrollmentService;
    this.announcementService = announcementService;
  }

  @PostMapping("/enrollments/course/{courseId}")
  public Map<String, Object> enroll(@PathVariable Long courseId,
                                    @AuthenticationPrincipal User user) {
    return enrollmentService.enroll(user, courseId);
  }

  @GetMapping("/enrollments/me")
  public List<Enrollment> getMyEnrollments(@AuthenticationPrincipal User user) {
    return enrollmentService.getMyEnrollments(user);
  }

  @PutMapping("/enrollments/course/{courseId}/progress")
  public Enrollment updateProgress(@PathVariable Long courseId,
                                   @Valid @RequestBody ProgressUpdateRequest request,
                                   @AuthenticationPrincipal User user) {
    return enrollmentService.updateProgress(user, courseId, request.getProgress());
  }

  @GetMapping("/payments")
  public List<Payment> getPayments() {
    return enrollmentService.getPayments();
  }

  @GetMapping("/announcements")
  public List<Announcement> getAnnouncements() {
    return announcementService.getAnnouncements();
  }
}
