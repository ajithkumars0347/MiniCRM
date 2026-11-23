
package com.AjithGroups.miniCRM.repository;

import com.AjithGroups.miniCRM.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeadRepository
        extends JpaRepository<Lead, Long> {
}
