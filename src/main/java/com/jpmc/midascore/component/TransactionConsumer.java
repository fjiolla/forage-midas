package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionConsumer {
    
    @Autowired
    private TransactionService transactionService;
    
    @KafkaListener(topics = "transactions", groupId = "midas-core")
    public void consumeTransaction(Transaction transaction) {
        System.out.println("Received transaction: " + transaction);
        transactionService.processTransaction(transaction);
    }
}