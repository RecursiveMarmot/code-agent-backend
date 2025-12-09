package com.timess.codeagent.model.dto.app;

import lombok.Data;

@Data
public class AppCreateRequest {
    private String name;
    private String initPrompt;
}

