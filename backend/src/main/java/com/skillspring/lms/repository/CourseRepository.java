package com.skillspring.lms.repository;

import com.skillspring.lms.model.Course;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
  List<Course> findAllByOrderByIdAsc();
}
