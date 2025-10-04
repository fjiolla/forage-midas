package com.jpmc.midascore.controller;

import com.jpmc.midascore.entity.User;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class BalanceController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/balance")
    public Balance getBalance(@RequestParam String userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Convert BigDecimal to float for the Balance class
            return new Balance(user.getBalance().floatValue());
        } else {
            // Return balance of 0 if user doesn't exist
            return new Balance(0.0f);
        }
    }
}
