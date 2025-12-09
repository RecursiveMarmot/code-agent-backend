package com.timess.codeagent.controller;


import com.timess.codeagent.exception.BaseResponse;
import com.timess.codeagent.exception.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查控制器。
 * 提供服务健康状态的接口。
 *
 * @author timess
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    /**
     * 健康检查接口。
     * @return 返回 "ok" 字符串，表示服务正常
     */
    @GetMapping("/ok")
    public BaseResponse<String> healthCheck() {
        return ResultUtils.success("ok");
    }
}
