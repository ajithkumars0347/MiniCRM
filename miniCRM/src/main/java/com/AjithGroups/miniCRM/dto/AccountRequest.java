package com.AjithGroups.miniCRM.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccountRequest(
        @NotBlank @Size(max = 150) String name
) {
}