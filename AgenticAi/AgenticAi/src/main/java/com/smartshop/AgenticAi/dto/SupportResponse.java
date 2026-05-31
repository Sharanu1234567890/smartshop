package com.smartshop.AgenticAi.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupportResponse {
    private String message;
    private String ticketId;
    private String status;
    private boolean escalated;
    private String intent;
}