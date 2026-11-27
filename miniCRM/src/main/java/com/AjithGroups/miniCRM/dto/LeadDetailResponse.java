package com.AjithGroups.miniCRM.dto;

public record LeadDetailResponse(
        Long id,
        String name,
        String email,
        String status,
        Long accountId,
        String accountName
) {
}