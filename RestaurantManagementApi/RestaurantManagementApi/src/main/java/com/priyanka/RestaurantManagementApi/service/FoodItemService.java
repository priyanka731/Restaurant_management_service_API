package com.priyanka.RestaurantManagementApi.service;

import com.priyanka.RestaurantManagementApi.model.FoodItem;
import com.priyanka.RestaurantManagementApi.repository.IFoodItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemService {
    @Autowired
    IFoodItemRepo foodRepo;
    public String addAllFoodItems(List<FoodItem> foodItem) {
        foodRepo.saveAll(foodItem);
        return "Food List added";
    }
}