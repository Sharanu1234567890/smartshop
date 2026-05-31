package com.smartshop.AgenticAi.agent;


import com.smartshop.AgenticAi.model.Escalation;
import com.smartshop.AgenticAi.repository.EscalationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EscalationTool {

    private final EscalationRepository escalationRepository;

    public String escalate(Long ticketId, String orderId,
                           String customerId, String reason) {
        if (escalationRepository.existsByTicketIdAndStatusNot(ticketId, "RESOLVED")) {
            return "Your case is already escalated. Our team will contact you soon.";
        }

        Escalation escalation = new Escalation();
        escalation.setTicketId(ticketId);
        escalation.setOrderId(orderId);
        escalation.setCustomerId(customerId);
        escalation.setReason(reason);
        escalationRepository.save(escalation);

        return "Your case has been escalated to our support team. " +
                "You will receive a response within 24 hours.";
    }
}