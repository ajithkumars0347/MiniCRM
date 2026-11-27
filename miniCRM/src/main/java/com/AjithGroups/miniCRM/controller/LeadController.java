package com.AjithGroups.miniCRM.controller;

import com.AjithGroups.miniCRM.dto.LeadRequest;
import com.AjithGroups.miniCRM.dto.LeadResponse;
import com.AjithGroups.miniCRM.service.LeadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.AjithGroups.miniCRM.dto.LeadPageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.Set;

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
    @PutMapping("/{leadId}/account/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void assignAccount(
            @PathVariable Long leadId,
            @PathVariable Long accountId) {

        leadService.assignAccount(leadId, accountId);
    }

    @GetMapping("/paged")
    public LeadPageResponse getPaginatedLeads(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        if (page < 0 || size < 1 || size > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Page must be non-negative and size must be between 1 and 100"
            );
        }

        if (!Set.of("id", "name", "email", "status")
                .contains(sortBy)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unsupported sort field"
            );
        }

        if (!direction.equalsIgnoreCase("asc")
                && !direction.equalsIgnoreCase("desc")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unsupported sort direction"
            );
        }

        return leadService.getPaginatedLeads(
                page, size, sortBy, direction
        );
    }

}
