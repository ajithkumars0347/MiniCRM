
package com.AjithGroups.miniCRM.controller;

import com.AjithGroups.miniCRM.dto.AccountRequest;
import com.AjithGroups.miniCRM.dto.AccountResponse;
import com.AjithGroups.miniCRM.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse create(
            @Valid @RequestBody AccountRequest request) {
        return accountService.create(request);
    }

    @GetMapping
    public List<AccountResponse> findAll() {
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public AccountResponse findById(@PathVariable Long id) {
        return accountService.findById(id);
    }
}
