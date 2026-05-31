package com.smartshop.AgenticAi.agent;


import com.smartshop.AgenticAi.exception.OrderNotFoundException;
import com.smartshop.AgenticAi.model.Order;
import com.smartshop.AgenticAi.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderTrackingTool {

    private final OrderRepository orderRepository;

    public String track(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        return String.format(
                "Order %s | Product: %s | Status: %s | Delivery: %s",
                order.getId(),
                order.getProductName(),
                order.getStatus(),
                order.getDeliveryDate()
        );
    }
}