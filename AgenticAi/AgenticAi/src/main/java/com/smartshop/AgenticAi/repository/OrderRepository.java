package com.smartshop.AgenticAi.repository;


import com.smartshop.AgenticAi.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByCustomerIdOrderByOrderDateDesc(Long customerId);
}