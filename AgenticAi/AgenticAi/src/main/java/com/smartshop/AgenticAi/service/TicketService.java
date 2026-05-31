package com.smartshop.AgenticAi.service;


import com.smartshop.AgenticAi.model.Ticket;
import com.smartshop.AgenticAi.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public List<Ticket> getCustomerTickets(Long customerId) {
        return ticketRepository.findByCustomerId(customerId);
    }

    public boolean isDuplicateTicket(String orderId, String intentType) {
        return ticketRepository
                .existsByOrderIdAndIntentTypeAndStatusNot(
                        orderId, intentType, "CLOSED");
    }
}