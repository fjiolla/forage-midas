package com.jpmc.midascore.service;

import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class IncentiveService {

    private final RestTemplate restTemplate;
    private static final String INCENTIVE_API_URL = "http://localhost:8080/incentive";

    public IncentiveService() {
        this.restTemplate = new RestTemplate();
    }

    public BigDecimal getIncentive(Transaction transaction) {
        try {
            Incentive incentive = restTemplate.postForObject(
                INCENTIVE_API_URL,
                transaction,
                Incentive.class
            );
            
            if (incentive != null && incentive.getAmount() != null) {
                System.out.println("Incentive received: " + incentive.getAmount());
                return incentive.getAmount();
            }
            
            return BigDecimal.ZERO;
        } catch (Exception e) {
            System.err.println("Error calling incentive API: " + e.getMessage());
            return BigDecimal.ZERO;
        }
    }
}
