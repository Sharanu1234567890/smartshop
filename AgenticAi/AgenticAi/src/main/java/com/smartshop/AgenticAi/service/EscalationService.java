package com.smartshop.AgenticAi.service;


import com.smartshop.AgenticAi.model.Escalation;
import com.smartshop.AgenticAi.repository.EscalationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EscalationService {

    private final EscalationRepository escalationRepository;

    public List<Escalation> getByTicketId(Long ticketId) {
        return escalationRepository.findByTicketId(ticketId);
    }

    public boolean alreadyEscalated(Long ticketId) {
        return escalationRepository
                .existsByTicketIdAndStatusNot(ticketId, "RESOLVED");
    }
}