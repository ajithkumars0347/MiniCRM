
package com.AjithGroups.miniCRM.repository;

import com.AjithGroups.miniCRM.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;

public interface LeadRepository
        extends JpaRepository<Lead, Long> {
    @EntityGraph(attributePaths = "account")
    Page<Lead> findAll(Pageable pageable);
}
