package com.priyanka.RestaurantManagementApi.service;

import com.priyanka.RestaurantManagementApi.model.Order;
import com.priyanka.RestaurantManagementApi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    UserService userService;
    @Autowired
    OrderService ordersService;

    public List<User> getAllUser() {

        return userService.getAllUser();
    }

    public List<Order> getAllOrders() {
        return ordersService.getAllOrder();

    }
}