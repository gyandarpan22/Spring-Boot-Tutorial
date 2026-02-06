package com.gyan.darpan.controller;

import com.gyan.darpan.dto.TransferAmountDto;
import com.gyan.darpan.service.AccountService;
import com.gyan.darpan.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("account")
public class AccountController {

    private final TransferService transferService;
    private final AccountService accountService;

    @Autowired
    public AccountController(TransferService transferService, AccountService accountService) {
        this.transferService = transferService;
        this.accountService = accountService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String transferAmount(@RequestBody TransferAmountDto transferAmountDto) throws Exception {
        transferService.transferAmount(transferAmountDto);
        return "Request Completed";
    }

    @PostMapping("debit")
    public String debit(@RequestBody TransferAmountDto transferAmountDto) throws Exception {
        accountService.debit(transferAmountDto.getFromAccountId(), transferAmountDto.getAmount());
        return "Request Completed";
    }
}
