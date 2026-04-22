package com.skillspring.lms.repository;

import com.skillspring.lms.model.Announcement;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
  List<Announcement> findAllByOrderByIdAsc();
}
