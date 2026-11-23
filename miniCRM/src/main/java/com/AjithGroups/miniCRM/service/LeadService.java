package com.AjithGroups.miniCRM.service;

import com.AjithGroups.miniCRM.dto.LeadRequest;
import com.AjithGroups.miniCRM.dto.LeadResponse;
import com.AjithGroups.miniCRM.entity.Lead;
import com.AjithGroups.miniCRM.exception.LeadNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.AjithGroups.miniCRM.repository.*;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class LeadService {
  private final LeadRepository leadRepository;
   public LeadService(LeadRepository leadRepository){
     this.leadRepository = leadRepository;
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
}
