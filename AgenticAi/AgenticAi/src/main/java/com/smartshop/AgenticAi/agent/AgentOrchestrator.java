package com.smartshop.AgenticAi.agent;


import com.smartshop.AgenticAi.dto.CustomerIntent;
import com.smartshop.AgenticAi.dto.SupportResponse;
import com.smartshop.AgenticAi.model.Action;
import com.smartshop.AgenticAi.model.Customer;
import com.smartshop.AgenticAi.model.Ticket;
import com.smartshop.AgenticAi.repository.ActionRepository;
import com.smartshop.AgenticAi.repository.CustomerRepository;
import com.smartshop.AgenticAi.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgentOrchestrator {

    private final IntentService intentService;
    private final OrderTrackingTool orderTrackingTool;
    private final ReturnTool returnTool;
    private final RefundTool refundTool;
    private final EscalationTool escalationTool;
    private final TicketRepository ticketRepository;
    private final ActionRepository actionRepository;
    private final CustomerRepository customerRepository;

    public SupportResponse handle(Long customerId, String message) {

        // Step 1 — extract intent
        CustomerIntent intent = intentService.extract(customerId, message);

        // Step 2 — create ticket
        Ticket ticket = createTicket(customerId, intent, message);

        // Step 3 — handle based on intent
        String result;
        boolean escalated = false;

        try {
            if (intent.isRequiresHuman()) {
                result = escalationTool.escalate(
                        ticket.getId(),
                        intent.getOrderId(),
                        customerId.toString(),
                        intent.getPrimaryIntent()
                );
                escalated = true;
            } else {
                result = switch (intent.getPrimaryIntent()) {
                    case "TRACK_ORDER" -> orderTrackingTool.track(intent.getOrderId());
                    case "RETURN"      -> returnTool.initiateReturn(intent.getOrderId());
                    case "REFUND"      -> refundTool.checkRefund(intent.getOrderId());
                    default            -> "Our team will assist you shortly.";
                };
            }
        } catch (Exception e) {
            log.error("Tool failed: {}", e.getMessage());
            result = e.getMessage();
        }

        // Step 4 — log action
        logAction(ticket.getId(), intent.getOrderId(),
                intent.getPrimaryIntent(), result);

        // Step 5 — update ticket
        ticket.setStatus(escalated ? "ESCALATED" : "RESOLVED");
        ticket.setResolvedBy(escalated ? "HUMAN" : "AUTO");
        ticketRepository.save(ticket);

        return SupportResponse.builder()
                .message(result)
                .ticketId(ticket.getId().toString())
                .status(ticket.getStatus())
                .escalated(escalated)
                .intent(intent.getPrimaryIntent())
                .build();
    }

    private Ticket createTicket(Long customerId,
                                CustomerIntent intent, String message) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Ticket ticket = new Ticket();
        ticket.setCustomer(customer);
        ticket.setOrderId(intent.getOrderId());
        ticket.setIntentType(intent.getPrimaryIntent());
        ticket.setCustomerMessage(message);
        return ticketRepository.save(ticket);
    }

    private void logAction(Long ticketId, String orderId,
                           String actionType, String result) {
        Action action = new Action();
        action.setTicketId(ticketId);
        action.setOrderId(orderId);
        action.setActionType(actionType);
        action.setResult(result);
        actionRepository.save(action);
    }
}