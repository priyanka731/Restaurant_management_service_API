package com.priyanka.RestaurantManagementApi.service;

import com.priyanka.RestaurantManagementApi.model.Order;
import com.priyanka.RestaurantManagementApi.model.User;
import com.priyanka.RestaurantManagementApi.repository.IOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    IOrderRepo ordersRepo;

    public List<Order> getAllOrder() {
        return ordersRepo.findAll();
    }

    public void saveOrder(Order orders) {

        orders.setLocalDateTime(LocalDateTime.now());
        ordersRepo.save(orders);
    }

    public Order getOrderForUser(User user) {

        return ordersRepo.findFirstByUser(user);
    }

    public void cancelOrder(Order orders) {

        ordersRepo.delete(orders);
    }
}

