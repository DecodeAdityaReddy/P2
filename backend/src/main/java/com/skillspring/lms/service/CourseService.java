package com.skillspring.lms.service;

import com.skillspring.lms.dto.CreateCourseRequest;
import com.skillspring.lms.model.Course;
import com.skillspring.lms.model.User;
import com.skillspring.lms.repository.CourseRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
  private final CourseRepository courseRepository;

  public CourseService(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  public List<Course> getCourses() {
    return courseRepository.findAllByOrderByIdAsc();
  }

  public Course createCourse(CreateCourseRequest request, User user) {
    Course course = new Course(
        null,
        request.getTitle(),
        request.getCategory(),
        request.getLevel(),
        user.getName(),
        request.getPrice(),
        request.getDuration(),
        request.getLessons(),
        5.0,
        request.getDescription()
    );
    return courseRepository.save(course);
  }
}
