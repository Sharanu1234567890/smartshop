package com.smartshop.AgenticAi.agent;


import com.smartshop.AgenticAi.exception.OrderNotFoundException;
import com.smartshop.AgenticAi.exception.ReturnNotEligibleException;
import com.smartshop.AgenticAi.model.Order;
import com.smartshop.AgenticAi.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class ReturnTool {

    private final OrderRepository orderRepository;

    public String initiateReturn(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (!"DELIVERED".equals(order.getStatus()))
            throw new ReturnNotEligibleException(
                    "Return not allowed. Order is " + order.getStatus());

        if (!order.getIsReturnable())
            throw new ReturnNotEligibleException(
                    "This product is non-returnable.");

        if (!"NONE".equals(order.getReturnStatus()))
            return "Return already " + order.getReturnStatus() +
                    " for order " + orderId;

        long daysSinceDelivery = ChronoUnit.DAYS.between(
                order.getDeliveryDate(), LocalDateTime.now());

        if (daysSinceDelivery > 7)
            throw new ReturnNotEligibleException(
                    "Return window expired. Returns allowed within 7 days of delivery.");

        order.setReturnStatus("REQUESTED");
        order.setReturnRequestedAt(LocalDateTime.now());
        orderRepository.save(order);

        return "Return initiated for order " + orderId +
                ". Pickup will be scheduled within 24-48 hours.";
    }
}