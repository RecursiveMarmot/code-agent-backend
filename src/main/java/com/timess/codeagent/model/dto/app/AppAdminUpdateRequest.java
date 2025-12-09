package com.timess.codeagent.model.dto.app;

import lombok.Data;

@Data
public class AppAdminUpdateRequest {
    private Long id;
    private String name;
    private String coverUrl;
    private Integer priority;
}

