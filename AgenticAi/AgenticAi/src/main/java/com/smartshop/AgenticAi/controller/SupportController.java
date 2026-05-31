package com.smartshop.AgenticAi.controller;


import com.smartshop.AgenticAi.agent.AgentOrchestrator;
import com.smartshop.AgenticAi.dto.SupportRequest;
import com.smartshop.AgenticAi.dto.SupportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/support")
@RequiredArgsConstructor
public class SupportController {

    private final AgentOrchestrator agentOrchestrator;

    @PostMapping
    public SupportResponse handle(@RequestBody SupportRequest request) {
        return agentOrchestrator.handle(
                request.getCustomerId(),
                request.getMessage()
        );
    }
}