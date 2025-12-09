package com.timess.codeagent.model.vo;

import lombok.Data;

@Data
public class AppVO {
    private Long id;
    private Long userId;
    private String name;
    private String initPrompt;
    private String coverUrl;
    private Integer priority;
    private Boolean isFeatured;
}

