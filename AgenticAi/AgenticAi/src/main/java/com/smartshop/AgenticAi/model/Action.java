package com.smartshop.AgenticAi.model;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "actions")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ticketId;
    private String orderId;

    // RETURN_INITIATED, REFUND_CHECKED, ORDER_TRACKED,
    // ESCALATED, GENERAL_RESPONSE
    private String actionType;

    // what happened — stored for audit
    @Column(length = 2000)
    private String result;

    // SUCCESS or FAILED
    private String status;

    private LocalDateTime timestamp;

    @PrePersist
    public void prePersist() {
        timestamp = LocalDateTime.now();
        if (status == null) status = "SUCCESS";
    }
}