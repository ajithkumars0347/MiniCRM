
package com.AjithGroups.miniCRM.dto;

import java.util.List;

public record LeadPageResponse(
        List<LeadDetailResponse> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext
) {
}
