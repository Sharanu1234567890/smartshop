package com.smartshop.AgenticAi.repository;


import com.smartshop.AgenticAi.model.Escalation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EscalationRepository extends JpaRepository<Escalation, Long> {
    List<Escalation> findByTicketId(Long ticketId);
    boolean existsByTicketIdAndStatusNot(Long ticketId, String status);
}