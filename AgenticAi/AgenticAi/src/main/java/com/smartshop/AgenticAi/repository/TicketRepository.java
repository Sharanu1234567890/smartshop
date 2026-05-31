package com.smartshop.AgenticAi.repository;


import com.smartshop.AgenticAi.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByCustomerId(Long customerId);
    List<Ticket> findByOrderIdAndIntentType(String orderId, String intentType);
    boolean existsByOrderIdAndIntentTypeAndStatusNot(
            String orderId, String intentType, String status);
}