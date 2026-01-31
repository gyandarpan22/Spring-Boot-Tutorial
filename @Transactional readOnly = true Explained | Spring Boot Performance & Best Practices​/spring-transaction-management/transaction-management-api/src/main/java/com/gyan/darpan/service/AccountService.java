package com.gyan.darpan.service;

import com.gyan.darpan.dto.TransferAmountDto;
import com.gyan.darpan.entity.Account;
import com.gyan.darpan.respository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional(rollbackFor = {Exception.class}, readOnly = true)
    public void transferAmount(TransferAmountDto transferAmountDto) throws Exception {
        Account fromAccount = accountRepository.findById(transferAmountDto.getFromAccountId()).get();
        Account toAccount = accountRepository.findById(transferAmountDto.getToAccountId()).get();

        if (fromAccount.getAmount().compareTo(transferAmountDto.getAmount()) < 0) {
            throw new RuntimeException("Insufficient Amount");
        }

        fromAccount.setAmount(fromAccount.getAmount().subtract(transferAmountDto.getAmount()));
        accountRepository.save(fromAccount);

        toAccount.setAmount(toAccount.getAmount().add(transferAmountDto.getAmount()));
        accountRepository.save(toAccount);
    }
}
