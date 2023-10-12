package com.priyanka.RestaurantManagementApi.service;

import com.priyanka.RestaurantManagementApi.model.AuthenticationToken;

import com.priyanka.RestaurantManagementApi.model.Order;
import com.priyanka.RestaurantManagementApi.model.User;
import com.priyanka.RestaurantManagementApi.model.dto.SignInInput;
import com.priyanka.RestaurantManagementApi.model.dto.SignUpOutput;
import com.priyanka.RestaurantManagementApi.repository.IAuthTokenRepo;
import com.priyanka.RestaurantManagementApi.repository.IFoodItemRepo;
import com.priyanka.RestaurantManagementApi.repository.IUserRepo;
import com.priyanka.RestaurantManagementApi.service.utils.EmailUtility;
import com.priyanka.RestaurantManagementApi.service.utils.HashingUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    IUserRepo userRepo;
    @Autowired
    IAuthTokenRepo authTokenRepo;
    @Autowired
    OrderService ordersService;
    @Autowired
    IFoodItemRepo foodRepo;
    public SignUpOutput signUpUser(User user) {
        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = user.getUserEmail();

        if(newEmail == null)
        {
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
        User existingUser = userRepo.findFirstByUserEmail(newEmail);
        if(existingUser != null)
        {
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //hash the password: encrypt the password
        try {
            String encryptedPassword = HashingUtility.encryptPassword(user.getUserPassword());
            user.setUserPassword(encryptedPassword);
            userRepo.save(user);
            return new SignUpOutput(signUpStatus, "user registered successfully!!!");
        }
        catch(Exception e)
        {
            signUpStatusMessage = "Internal error occurred during sign up";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
    }
    public List<User> getAllUser() {
        return userRepo.findAll();
    }
    public String signInUser(SignInInput signInInput) {
        String signInStatusMessage = null;
        String signInEmail = signInInput.getEmail();
        if(signInEmail == null)
        {
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;

        }
        User existingUser = userRepo.findFirstByUserEmail(signInEmail);
        if(existingUser == null)
        {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;

        }
        //match passwords :
        //hash the password: encrypt the password
        try {
            String encryptedPassword = HashingUtility.encryptPassword(signInInput.getPassword());
            if(existingUser.getUserPassword().equals(encryptedPassword))
            {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken  = new AuthenticationToken(existingUser);
                authTokenRepo.save(authToken);

                EmailUtility.sendEmail(signInEmail,"email testing",authToken.getTokenValue());
                return "Token sent to your email";
            }
            else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        }
        catch(Exception e)
        {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }
    }
    public String sigOutUser(String email) {
        User user = userRepo.findFirstByUserEmail(email);
        authTokenRepo.delete(authTokenRepo.findFirstByUser(user));
        return "Patient Signed out successfully";
    }

    public boolean orderFood(Order orders) {
        Long foodId;
        foodId = Long.valueOf(orders.getExpression().toString());
        boolean isFoodValid = foodRepo.existsById(foodId);

        Long userId = Long.valueOf(orders.getExpression().toString());
        boolean isUserValid = userRepo.existsById(userId);

        if(isFoodValid&& isUserValid)
        {

            ordersService.saveOrder(orders);
            return true;
        }
        else {
            return false;
        }
    }

    public void cancelOrder(String email) {

        User user = userRepo.findFirstByUserEmail(email);

        Order orders = ordersService.getOrderForUser(user);

        ordersService.cancelOrder(new Order());
    }

}