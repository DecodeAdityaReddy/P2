package com.skillspring.lms.controller;

import com.skillspring.lms.dto.CreateCourseRequest;
import com.skillspring.lms.model.Course;
import com.skillspring.lms.model.Role;
import com.skillspring.lms.model.User;
import com.skillspring.lms.service.CourseService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
  private final CourseService courseService;

  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  @GetMapping
  public List<Course> getCourses() {
    return courseService.getCourses();
  }

  @PostMapping
  public Course createCourse(@Valid @RequestBody CreateCourseRequest request,
                             @AuthenticationPrincipal User user) {
    if (user.getRole() != Role.INSTRUCTOR && user.getRole() != Role.ADMIN) {
      throw new ResponseStatusException(FORBIDDEN, "Only instructors or admins can create courses");
    }

    return courseService.createCourse(request, user);
  }
}
