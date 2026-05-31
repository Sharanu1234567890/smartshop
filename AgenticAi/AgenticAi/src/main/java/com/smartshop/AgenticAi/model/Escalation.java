package com.smartshop.AgenticAi.model;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "escalations")
public class Escalation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ticketId;
    private String orderId;
    private String customerId;

    // why escalated
    // DAMAGED, HIGH_VALUE, FRAUD, WRONG_PRODUCT,
    // CUSTOMER_REQUESTED, ABUSIVE, AI_FAILED
    private String reason;

    // PENDING, ASSIGNED, RESOLVED
    private String status;

    // which human agent is handling
    private String assignedTo;

    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        if (status == null) status = "PENDING";
    }
}