package com.smartshop.AgenticAi.agent;


import com.smartshop.AgenticAi.exception.OrderNotFoundException;
import com.smartshop.AgenticAi.model.Order;
import com.smartshop.AgenticAi.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefundTool {

    private final OrderRepository orderRepository;

    public String checkRefund(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        return switch (order.getRefundStatus()) {
            case "NONE" -> "No refund initiated for order " + orderId +
                    ". Please raise a return request first.";
            case "INITIATED" -> "Refund initiated. Will reflect in 5-7 business days.";
            case "PROCESSED" -> "Refund processed successfully.";
            case "FAILED" -> "Refund failed. Escalating to support team.";
            default -> "Refund status unknown. Escalating to support.";
        };
    }
}