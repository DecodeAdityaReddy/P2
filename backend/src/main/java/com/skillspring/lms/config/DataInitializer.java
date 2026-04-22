package com.skillspring.lms.config;

import com.skillspring.lms.model.Announcement;
import com.skillspring.lms.model.Course;
import com.skillspring.lms.model.Role;
import com.skillspring.lms.model.User;
import com.skillspring.lms.repository.AnnouncementRepository;
import com.skillspring.lms.repository.CourseRepository;
import com.skillspring.lms.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
  @Bean
  CommandLineRunner seedDatabase(
      UserRepository userRepository,
      CourseRepository courseRepository,
      AnnouncementRepository announcementRepository,
      PasswordEncoder passwordEncoder
  ) {
    return args -> {
      if (userRepository.count() == 0) {
        userRepository.save(new User(null, "Aarav Student", "student@lms.com",
            passwordEncoder.encode("password"), Role.STUDENT));
        userRepository.save(new User(null, "Ishita Instructor", "instructor@lms.com",
            passwordEncoder.encode("password"), Role.INSTRUCTOR));
        userRepository.save(new User(null, "Neel Admin", "admin@lms.com",
            passwordEncoder.encode("password"), Role.ADMIN));
      }

      if (courseRepository.count() == 0) {
        courseRepository.save(new Course(null, "Java for Backend Development", "Backend", "Intermediate",
            "Ishita Sharma", 1999, "8 weeks", 24, 4.8,
            "Build production-ready Java backend skills with APIs, exception handling, and layered architecture."));
        courseRepository.save(new Course(null, "Spring Boot REST API Masterclass", "Backend", "Advanced",
            "Rohan Mehta", 2499, "10 weeks", 30, 4.9,
            "Design secure REST APIs with Spring Boot, JPA, validation, and real-world service patterns."));
        courseRepository.save(new Course(null, "React.js Frontend Essentials", "Frontend", "Beginner",
            "Kavya Nair", 1499, "6 weeks", 18, 4.7,
            "Create responsive single-page applications with React components, routing, and state-driven UI."));
        courseRepository.save(new Course(null, "JWT Authentication in Modern Apps", "Security", "Intermediate",
            "Neel Verma", 1799, "5 weeks", 16, 4.6,
            "Implement secure authentication flows using JWT, protected routes, token storage, and role checks."));
      }

      if (announcementRepository.count() == 0) {
        announcementRepository.save(new Announcement(null, "New Java backend track available", "All users"));
        announcementRepository.save(new Announcement(null, "Weekend live mentor session on Spring Security", "Students"));
      }
    };
  }
}
