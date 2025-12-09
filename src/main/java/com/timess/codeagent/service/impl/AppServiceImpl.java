package com.timess.codeagent.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.timess.codeagent.exception.BusinessException;
import com.timess.codeagent.exception.ErrorCode;
import com.timess.codeagent.model.dto.app.*;
import com.timess.codeagent.model.vo.AppVO;
import org.springframework.transaction.annotation.Transactional;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.timess.codeagent.model.entity.App;
import com.timess.codeagent.mapper.AppMapper;
import com.timess.codeagent.service.AppService;
import org.springframework.stereotype.Service;
import com.timess.codeagent.model.dto.app.AppQueryRequest;

import java.util.Objects;

/**
 * 应用 服务层实现。
 *
 * @author timess
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App>  implements AppService{

    @Override
    @Transactional
    public Long createApp(AppCreateRequest request, Long userId) {
        if (StrUtil.hasBlank(request.getName(), request.getInitPrompt())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        App app = new App();
        app.setAppName(request.getName());
        app.setInitPrompt(request.getInitPrompt());
        app.setUserId(userId);
        app.setPriority(0);
        boolean result = this.save(app);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建失败");
        }
        return app.getId();
    }

    @Override
    @Transactional
    public boolean updateApp(AppUpdateRequest request, Long userId) {
        if (request.getId() == null || StrUtil.isBlank(request.getName())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        App app = this.getById(request.getId());
        if (app == null || !Objects.equals(app.getUserId(), userId)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限");
        }
        app.setAppName(request.getName());
        return this.updateById(app);
    }

    @Override
    @Transactional
    public boolean deleteApp(Long appId, Long userId) {
        App app = this.getById(appId);
        if (app == null || !Objects.equals(app.getUserId(), userId)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限");
        }
        return this.removeById(appId);
    }

    @Override
    public AppVO getAppById(Long appId, Long userId) {
        App app = this.getById(appId);
        if (app == null || !Objects.equals(app.getUserId(), userId)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限");
        }
        return toVO(app);
    }

    private <T, R> Page<R> convertPage(Page<T> page, java.util.function.Function<T, R> mapper) {
        Page<R> result = new Page<>(page.getPageNumber(), page.getPageSize(), page.getTotalRow());
        if (page.getRecords() != null) {
            result.setRecords(page.getRecords().stream().map(mapper).toList());
        }
        return result;
    }

    @Override
    public Page<AppVO> listMyApps(AppQueryRequest request, Long userId) {
        QueryWrapper qw = QueryWrapper.create()
                .eq("userId", userId)
                .like( "appName", request.getName())
                .orderBy("id", false);
        Page<App> page = this.page(new Page<>(request.getPageNum(), request.getPageSize()), qw);
        return convertPage(page, this::toVO);
    }

    @Override
    public Page<AppVO> listFeaturedApps(AppQueryRequest request) {
        QueryWrapper qw = QueryWrapper.create()
                .eq("priority", 1)
                .like("appName", request.getName())
                .orderBy("id", false);
        Page<App> page = this.page(new Page<>(request.getPageNum(), request.getPageSize()), qw);
        return convertPage(page, this::toVO);
    }

    @Override
    @Transactional
    public boolean adminDeleteApp(Long appId) {
        return this.removeById(appId);
    }

    @Override
    @Transactional
    public boolean adminUpdateApp(AppAdminUpdateRequest request) {
        if (request.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        App app = this.getById(request.getId());
        if (app == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "应用不存在");
        }
        if (StrUtil.isNotBlank(request.getName())) {
            app.setAppName(request.getName());
        }
        if (StrUtil.isNotBlank(request.getCoverUrl())) {
            app.setCover(request.getCoverUrl());
        }
        if (request.getPriority() != null) {
            app.setPriority(request.getPriority());
        }
        return this.updateById(app);
    }

    @Override
    public Page<AppVO> adminListApps(AppAdminQueryRequest request) {
        QueryWrapper qw = QueryWrapper.create()
                .eq("id", request.getId())
                .eq("userId", request.getUserId())
                .like( "appName", request.getName())
                .eq("cover", request.getCoverUrl())
                .eq( "priority", request.getPriority())
                .eq("priority", request.getIsFeatured() ? 1 : 0)
                .orderBy("priority", false);
        Page<App> page = this.page(new Page<>(request.getPageNum(), request.getPageSize()), qw);
        return convertPage(page, this::toVO);
    }

    @Override
    public AppVO adminGetAppById(Long appId) {
        App app = this.getById(appId);
        if (app == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "应用不存在");
        }
        return toVO(app);
    }

    private AppVO toVO(App app) {
        if (app == null) {
            return null;
        }
        AppVO vo = new AppVO();
        vo.setId(app.getId());
        vo.setUserId(app.getUserId());
        vo.setName(app.getAppName());
        vo.setInitPrompt(app.getInitPrompt());
        vo.setCoverUrl(app.getCover());
        vo.setPriority(app.getPriority());
        vo.setIsFeatured(app.getPriority() != null && app.getPriority() > 0);
        return vo;
    }
}
