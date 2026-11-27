
package com.AjithGroups.miniCRM.service;

import com.AjithGroups.miniCRM.dto.AccountRequest;
import com.AjithGroups.miniCRM.dto.AccountResponse;
import com.AjithGroups.miniCRM.entity.Account;
import com.AjithGroups.miniCRM.exception.AccountNotFoundException;
import com.AjithGroups.miniCRM.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public AccountResponse create(AccountRequest request) {
        Account account = new Account(request.name());
        Account saved = accountRepository.save(account);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AccountResponse findById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        return toResponse(account);
    }

    private AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getName()
        );
    }
}
