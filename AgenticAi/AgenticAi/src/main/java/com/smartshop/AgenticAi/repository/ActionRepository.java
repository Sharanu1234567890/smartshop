package com.smartshop.AgenticAi.repository;


import com.smartshop.AgenticAi.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {
    List<Action> findByTicketId(Long ticketId);
    List<Action> findByOrderId(String orderId);
}