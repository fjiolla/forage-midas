package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {

    private int count = 0;

    @KafkaListener(topics = "${general.kafka-topic}", 
                   groupId = "midas-core-group",
                   containerFactory = "kafkaListenerContainerFactory")
    public void listen(Transaction transaction) {
        count++;
        System.out.println("========================================");
        System.out.println("TRANSACTION #" + count);
        System.out.println("AMOUNT: " + transaction.getAmount());
        System.out.println("Sender: " + transaction.getSenderId() + " -> Recipient: " + transaction.getRecipientId());
        System.out.println("========================================");
        
        if (count >= 4) {
            System.out.println("\n\n*** FIRST 4 TRANSACTIONS RECEIVED! Press Ctrl+C to stop ***\n\n");
        }
    }
}
