package com.priyanka.RestaurantManagementApi.repository;


import com.priyanka.RestaurantManagementApi.model.AuthenticationToken;
import com.priyanka.RestaurantManagementApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthTokenRepo extends JpaRepository<AuthenticationToken,Long> {
    AuthenticationToken findFirstByTokenValue(String authTokenValue);
    AuthenticationToken findFirstByUser(User user);
}
