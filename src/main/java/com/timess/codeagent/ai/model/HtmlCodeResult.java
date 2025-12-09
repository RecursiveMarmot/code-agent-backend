package com.timess.codeagent.ai.model;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;

/**
 * @author eternal
 */
@Data
@Description("生成 HTML 代码文件的结果")
public class HtmlCodeResult {

    @Description("HTML代码")
    private String htmlCode;

    @Description("生成代码的描述")
    private String description;
}

