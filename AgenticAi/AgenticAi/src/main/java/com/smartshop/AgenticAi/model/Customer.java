package com.smartshop.AgenticAi.model;

//package com.smartshop.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;

    // total orders placed — used to identify VIP customers
    private Integer totalOrders;

    // VIP if totalOrders > 10 or totalSpend > 50000
    private Boolean isVip;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        if (totalOrders == null) totalOrders = 0;
        if (isVip == null) isVip = false;
    }
}