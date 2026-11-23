package com.AjithGroups.miniCRM.controller;

import com.AjithGroups.miniCRM.dto.LeadRequest;
import com.AjithGroups.miniCRM.dto.LeadResponse;
import com.AjithGroups.miniCRM.service.LeadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leads")
public class LeadController {

    private final LeadService leadService;
     LeadController(LeadService leadService){
        this.leadService = leadService;
     }

     @GetMapping
     public List<LeadResponse> getAllLeads(LeadService leadService){
         return leadService.getAllLeads();
     }

     @GetMapping("/{id}")
     public LeadResponse getLeadById(@PathVariable Long id){
         return leadService.getLeadById(id);
     }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LeadResponse createLead(
            @Valid @RequestBody LeadRequest request) {

        return leadService.createLead(request);
    }

    @PutMapping("/{id}")
    public LeadResponse updateLead(
            @PathVariable Long id,
            @Valid @RequestBody LeadRequest request) {

        return leadService.updateLead(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLead(@PathVariable Long id) {

        leadService.deleteLead(id);
    }
}
