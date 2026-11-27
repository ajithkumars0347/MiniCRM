package com.AjithGroups.miniCRM.repository;

import com.AjithGroups.miniCRM.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository
        extends JpaRepository<Account, Long> {
}