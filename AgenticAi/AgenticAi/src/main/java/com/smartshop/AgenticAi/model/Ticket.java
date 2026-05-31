package com.smartshop.AgenticAi.model;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String orderId;

    // TRACK_ORDER, RETURN, REFUND, DAMAGED, WRONG_PRODUCT,
    // MISSING_ITEM, FRAUD, GENERAL
    private String intentType;

    // original message from customer
    @Column(length = 2000)
    private String customerMessage;

    // OPEN, IN_PROGRESS, RESOLVED, ESCALATED, CLOSED
    private String status;

    // AUTO — handled by AI, HUMAN — handled by agent
    private String resolvedBy;

    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        if (status == null) status = "OPEN";
    }
}