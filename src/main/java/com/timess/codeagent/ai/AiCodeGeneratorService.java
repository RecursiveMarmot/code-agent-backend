package com.timess.codeagent.ai;

import com.timess.codeagent.ai.model.HtmlCodeResult;
import com.timess.codeagent.ai.model.MultiFileCodeResult;
import dev.langchain4j.service.SystemMessage;
import reactor.core.publisher.Flux;

/**
 * @author eternal
 */
public interface AiCodeGeneratorService {

    /**
     * 生成 HTML 代码
     *
     * @param userMessage 用户消息
     * @return 生成的代码结果
     */
    @SystemMessage(fromResource = "prompt/prompt-single-file.txt")
    HtmlCodeResult generateHtmlCode(String userMessage);

    /**
     * 生成多文件代码
     *
     * @param userMessage 用户消息
     * @return 生成的代码结果
     */
    @SystemMessage(fromResource = "prompt/prompt-multi-file.txt")
    MultiFileCodeResult generateMultiFileCode(String userMessage);


    /**
     * 生成 HTML 代码(流式)
     *
     * @param userMessage 用户消息
     * @return 生成的代码结果
     */
    @SystemMessage(fromResource = "prompt/prompt-single-file.txt")
    Flux<String> generateHtmlCodeStream(String userMessage);

    /**
     * 生成多文件代码(流式)
     *
     * @param userMessage 用户消息
     * @return 生成的代码结果
     */
    @SystemMessage(fromResource = "prompt/prompt-multi-file.txt")
    Flux<String> generateMultiFileCodeStream(String userMessage);
}
