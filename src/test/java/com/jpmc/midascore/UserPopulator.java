package com.jpmc.midascore;

import com.jpmc.midascore.entity.User;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UserPopulator {

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private UserRepository userRepository;

    public void populate() {
        String[] userLines = fileLoader.loadStrings("/test_data/lkjhgfdsa.hjkl");
        for (int i = 0; i < userLines.length; i++) {
            String[] userData = userLines[i].split(", ");
            User user = new User();
            // Use the index (1-based) as the ID to match transaction sender/recipient IDs
            user.setId(String.valueOf(i + 1));
            user.setName(userData[0]);
            user.setBalance(BigDecimal.valueOf(Float.parseFloat(userData[1])));
            userRepository.save(user);
        }
    }
}