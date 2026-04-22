package com.skillspring.lms.repository;

import com.skillspring.lms.model.Payment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
  List<Payment> findAllByOrderByPaidAtAsc();
}
