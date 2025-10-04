package com.jpmc.midascore.service;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.User;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRecordRepository transactionRecordRepository;

    @Autowired
    private IncentiveService incentiveService;

    @Transactional
    public void processTransaction(Transaction transaction) {
        // Convert long IDs to String for repository lookup
        String senderIdStr = String.valueOf(transaction.getSenderId());
        String recipientIdStr = String.valueOf(transaction.getRecipientId());
        
        // Find sender and recipient
        Optional<User> senderOpt = userRepository.findById(senderIdStr);
        Optional<User> recipientOpt = userRepository.findById(recipientIdStr);

        // Validate sender and recipient exist
        if (!senderOpt.isPresent() || !recipientOpt.isPresent()) {
            System.out.println("Invalid sender or recipient ID");
            return;
        }

        User sender = senderOpt.get();
        User recipient = recipientOpt.get();

        // Convert float amount to BigDecimal
        BigDecimal amount = BigDecimal.valueOf(transaction.getAmount());

        // Validate sender has sufficient balance
        if (sender.getBalance().compareTo(amount) < 0) {
            System.out.println("Insufficient balance for sender: " + sender.getId());
            return;
        }

        // Get incentive from the Incentive API
        BigDecimal incentive = incentiveService.getIncentive(transaction);

        // Update balances
        sender.setBalance(sender.getBalance().subtract(amount));
        // Add both the transaction amount AND the incentive to recipient
        recipient.setBalance(recipient.getBalance().add(amount).add(incentive));

        // Save updated users
        userRepository.save(sender);
        userRepository.save(recipient);

        // Create and save transaction record
        TransactionRecord record = new TransactionRecord();
        record.setSender(sender);
        record.setRecipient(recipient);
        record.setAmount(amount);
        record.setIncentive(incentive);
        record.setTransactionTime(java.time.LocalDateTime.now());

        transactionRecordRepository.save(record);

        System.out.println("Transaction processed successfully: " + 
                          sender.getId() + " -> " + recipient.getId() + 
                          " Amount: " + amount + " Incentive: " + incentive);
    }
}
