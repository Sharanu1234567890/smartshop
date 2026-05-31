package com.smartshop.AgenticAi.service;


import com.smartshop.AgenticAi.exception.OrderNotFoundException;
import com.smartshop.AgenticAi.model.Order;
import com.smartshop.AgenticAi.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order getOrder(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    public List<Order> getCustomerOrders(Long customerId) {
        return orderRepository
                .findByCustomerIdOrderByOrderDateDesc(customerId);
    }
}