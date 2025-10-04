package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.User;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.UserRepository;
import com.jpmc.midascore.repository.UserRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Profile("!test")  // Don't run this in test profile
public class DatabaseConduit implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRecordRepository userRecordRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize users with starting balances
        User waldorf = new User();
        waldorf.setId("waldorf");
        waldorf.setName("Waldorf");
        waldorf.setBalance(BigDecimal.valueOf(1000.0));

        User statler = new User();
        statler.setId("statler");
        statler.setName("Statler");
        statler.setBalance(BigDecimal.valueOf(1000.0));

        User fozzie = new User();
        fozzie.setId("fozzie");
        fozzie.setName("Fozzie");
        fozzie.setBalance(BigDecimal.valueOf(1000.0));

        User kermit = new User();
        kermit.setId("kermit");
        kermit.setName("Kermit");
        kermit.setBalance(BigDecimal.valueOf(1000.0));

        // Save users to database
        userRepository.save(waldorf);
        userRepository.save(statler);
        userRepository.save(fozzie);
        userRepository.save(kermit);

        System.out.println("Database initialized with users");
    }

    public void save(UserRecord userRecord) {
        userRecordRepository.save(userRecord);
    }
}