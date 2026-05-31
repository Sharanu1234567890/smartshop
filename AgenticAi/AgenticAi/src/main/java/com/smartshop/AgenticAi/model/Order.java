package com.smartshop.AgenticAi.model;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    private String id; // e.g. ORD123 — not auto generated

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String productName;
    private String productCategory;
    private Double amount;

    // PLACED, PROCESSING, SHIPPED, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
    private String status;

    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;

    // NONE, REQUESTED, APPROVED, PICKED_UP, COMPLETED
    private String returnStatus;

    // NONE, INITIATED, PROCESSED, FAILED
    private String refundStatus;

    private LocalDateTime returnRequestedAt;

    // true if amount > 10000 — always escalate these
    private Boolean isHighValue;

    // non-returnable items like medicines, personalized products
    private Boolean isReturnable;

    @PrePersist
    public void prePersist() {
        if (returnStatus == null) returnStatus = "NONE";
        if (refundStatus == null) refundStatus = "NONE";
        if (isHighValue == null) isHighValue = amount != null && amount > 10000;
        if (isReturnable == null) isReturnable = true;
    }
}