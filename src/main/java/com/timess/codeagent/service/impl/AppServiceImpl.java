package com.timess.codeagent.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.timess.codeagent.model.entity.App;
import com.timess.codeagent.mapper.AppMapper;
import com.timess.codeagent.service.AppService;
import org.springframework.stereotype.Service;

/**
 * 应用 服务层实现。
 *
 * @author timess
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App>  implements AppService{

}
