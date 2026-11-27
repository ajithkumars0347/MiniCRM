package com.AjithGroups.miniCRM.service;

import com.AjithGroups.miniCRM.dto.LeadRequest;
import com.AjithGroups.miniCRM.dto.LeadResponse;
import com.AjithGroups.miniCRM.entity.Account;
import com.AjithGroups.miniCRM.entity.Lead;
import com.AjithGroups.miniCRM.exception.AccountNotFoundException;
import com.AjithGroups.miniCRM.exception.LeadNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.AjithGroups.miniCRM.repository.*;
import java.util.List;
import com.AjithGroups.miniCRM.dto.LeadDetailResponse;
import com.AjithGroups.miniCRM.dto.LeadPageResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.NoSuchElementException;


@Service
public class LeadService {
  private final LeadRepository leadRepository;

    private final AccountRepository accountRepository;

    public LeadService(
            LeadRepository leadRepository,
            AccountRepository accountRepository) {

        this.leadRepository = leadRepository;
        this.accountRepository = accountRepository;
    }

   @Transactional
  public LeadResponse createLead(LeadRequest request){
     Lead lead = new Lead(
             request.name(),
             request.email(),
             request.status()
     );

     Lead savedLead = leadRepository.save(lead);

     return toResponse(savedLead);
  }
    private LeadResponse toResponse(Lead lead) {

        return new LeadResponse(
                lead.getId(),
                lead.getName(),
                lead.getEmail(),
                lead.getStatus()
        );
    }

    @Transactional(readOnly = true)
    public List<LeadResponse> getAllLeads() {

        return leadRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public LeadResponse getLeadById(Long id) {

        Lead lead = findLead(id);

        return toResponse(lead);
    }
    private Lead findLead(Long id) {

        return leadRepository.findById(id)
                .orElseThrow(() ->
                        new LeadNotFoundException(id)
                );
    }
    @Transactional
    public LeadResponse updateLead(
            Long id, LeadRequest request) {

        Lead lead = findLead(id);

        lead.setName(request.name());
        lead.setEmail(request.email());
        lead.setStatus(request.status());

        return toResponse(leadRepository.save(lead));
    }

    @Transactional
    public void deleteLead(Long id) {

        Lead lead = findLead(id);

        leadRepository.delete(lead);
    }

    @Transactional
    public void assignAccount(Long leadId, Long accountId) {

        Lead lead = findLead(leadId);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException(accountId)
                );

        lead.setAccount(account);
    }

    @Transactional(readOnly = true)
    public LeadPageResponse getPaginatedLeads(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort.Direction sortDirection =
                Sort.Direction.fromString(direction);

        Sort sort = Sort.by(
                new Sort.Order(sortDirection, sortBy),
                new Sort.Order(Sort.Direction.ASC, "id")
        );

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Lead> leadPage = leadRepository.findAll(pageable);

        List<LeadDetailResponse> content = leadPage
                .getContent()
                .stream()
                .map(this::toDetailResponse)
                .toList();

        return new LeadPageResponse(
                content,
                leadPage.getNumber(),
                leadPage.getSize(),
                leadPage.getTotalElements(),
                leadPage.getTotalPages(),
                leadPage.hasNext()
        );
    }

    private LeadDetailResponse toDetailResponse(Lead lead) {

        Account account = lead.getAccount();

        return new LeadDetailResponse(
                lead.getId(),
                lead.getName(),
                lead.getEmail(),
                lead.getStatus(),
                account != null ? account.getId() : null,
                account != null ? account.getName() : null
        );
    }

}
