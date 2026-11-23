package com.AjithGroups.miniCRM.dto;

public record LeadResponse (
  Long id,
  String name,
  String email,
  String status
) {
}
