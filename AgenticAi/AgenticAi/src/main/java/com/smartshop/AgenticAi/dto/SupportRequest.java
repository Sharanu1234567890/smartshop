package com.smartshop.AgenticAi.dto;


import lombok.Data;

@Data
public class SupportRequest {
    private Long customerId;
    private String message;
}