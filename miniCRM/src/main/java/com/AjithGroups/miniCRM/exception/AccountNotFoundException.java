package com.AjithGroups.miniCRM.exception;

public class AccountNotFoundException
        extends RuntimeException {

    public AccountNotFoundException(Long id) {
        super("Account not found with id: " + id);
    }
}