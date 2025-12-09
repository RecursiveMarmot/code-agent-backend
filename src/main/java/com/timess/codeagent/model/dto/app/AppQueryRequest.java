package com.timess.codeagent.model.dto.app;

import lombok.Data;

@Data
public class AppQueryRequest {
    private String name;
    private Integer pageNum = 1;
    private Integer pageSize = 20;
}


