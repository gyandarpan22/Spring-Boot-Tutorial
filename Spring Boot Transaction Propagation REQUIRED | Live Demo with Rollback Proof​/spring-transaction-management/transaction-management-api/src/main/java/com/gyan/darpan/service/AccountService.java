package com.gyan.darpan.service;

import com.gyan.darpan.entity.Account;
import com.gyan.darpan.respository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void debit(long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId).get();

        if (account.getAmount().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient Amount");
        }

        account.setAmount(account.getAmount().subtract(amount));
        accountRepository.save(account);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void credit(long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId).get();

        account.setAmount(account.getAmount().add(amount));
        accountRepository.save(account);
    }

}
