package com.priyanka.RestaurantManagementApi.repository;

import com.priyanka.RestaurantManagementApi.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFoodItemRepo extends JpaRepository<FoodItem,Long> {
}
