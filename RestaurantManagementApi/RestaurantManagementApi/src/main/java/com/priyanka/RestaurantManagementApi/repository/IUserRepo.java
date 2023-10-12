package com.priyanka.RestaurantManagementApi.repository;

import com.priyanka.RestaurantManagementApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepo extends JpaRepository<User,Long> {
    User findFirstByUserEmail(String email);
}
