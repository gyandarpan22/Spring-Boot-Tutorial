package com.gyan.darpan.service;

import com.gyan.darpan.dto.TransferAmountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {
    private final AccountService accountService;

    @Autowired
    public TransferService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Transactional
    public void transferAmount(TransferAmountDto transferAmountDto) {
        accountService.debit(transferAmountDto.getFromAccountId(), transferAmountDto.getAmount());
        accountService.credit(transferAmountDto.getToAccountId(), transferAmountDto.getAmount());

        //throw new RuntimeException("Required Propagation testing");
    }
}
