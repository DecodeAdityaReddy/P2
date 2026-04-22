package com.skillspring.lms.repository;

import com.skillspring.lms.model.Enrollment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
  Optional<Enrollment> findByCourseIdAndEmail(Long courseId, String email);

  List<Enrollment> findByEmailOrderByIdAsc(String email);

  List<Enrollment> findAllByOrderByIdAsc();
}
