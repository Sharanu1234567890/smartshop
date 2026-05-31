package com.smartshop.AgenticAi.dto;


import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class CustomerIntent {
    private List<String> intents;
    private String primaryIntent;
    private String orderId;
    private Long customerId;
    private String language;
    private boolean requiresHuman;
    private String rawMessage;
    private Map<String, Object> extras;
}