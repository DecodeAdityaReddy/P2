package com.skillspring.lms.service;

import com.skillspring.lms.model.Announcement;
import com.skillspring.lms.repository.AnnouncementRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AnnouncementService {
  private final AnnouncementRepository announcementRepository;

  public AnnouncementService(AnnouncementRepository announcementRepository) {
    this.announcementRepository = announcementRepository;
  }

  public List<Announcement> getAnnouncements() {
    return announcementRepository.findAllByOrderByIdAsc();
  }
}
