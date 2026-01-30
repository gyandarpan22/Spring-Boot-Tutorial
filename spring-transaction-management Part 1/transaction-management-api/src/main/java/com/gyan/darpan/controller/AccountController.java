package com.gyan.darpan.controller;

import com.gyan.darpan.dto.TransferAmountDto;
import com.gyan.darpan.service.AccountService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String transferAmount(@RequestBody TransferAmountDto transferAmountDto) throws Exception {
        accountService.transferAmount(transferAmountDto);
        return "Request Completed";
    }
}
