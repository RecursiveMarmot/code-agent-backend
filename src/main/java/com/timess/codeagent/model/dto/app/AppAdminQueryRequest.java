package com.timess.codeagent.model.dto.app;

import lombok.Data;

@Data
public class AppAdminQueryRequest {
    private Long id;
    private Long userId;
    private String name;
    private String coverUrl;
    private Integer priority;
    private Boolean isFeatured;
    private Integer pageNum = 1;
    private Integer pageSize = 20;
}

