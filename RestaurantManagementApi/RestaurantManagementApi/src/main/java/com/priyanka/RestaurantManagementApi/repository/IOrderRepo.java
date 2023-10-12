package com.priyanka.RestaurantManagementApi.repository;

import com.priyanka.RestaurantManagementApi.model.Order;
import com.priyanka.RestaurantManagementApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepo extends JpaRepository<Order,Long> {
    Order findFirstByUser(User user);
}
