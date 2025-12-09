package com.timess.codeagent.service;

import com.mybatisflex.core.service.IService;
import com.mybatisflex.core.paginate.Page;
import com.timess.codeagent.model.dto.app.*;
import com.timess.codeagent.model.vo.AppVO;
import com.timess.codeagent.model.entity.App;

import java.util.List;

/**
 * 应用 服务层。
 * 提供应用的增删改查、分页、条件查询等业务接口。
 *
 * @author timess
 */
public interface AppService extends IService<App> {
    /**
     * 用户创建应用
     * @param request 创建请求体
     * @param userId 用户ID
     * @return 新应用ID
     */
    Long createApp(AppCreateRequest request, Long userId);
    /**
     * 用户修改自己的应用
     * @param request 修改请求体
     * @param userId 用户ID
     * @return 是否修改成功
     */
    boolean updateApp(AppUpdateRequest request, Long userId);
    /**
     * 用户删除自己的应用
     * @param appId 应用ID
     * @param userId 用户ID
     * @return 是否删除成功
     */
    boolean deleteApp(Long appId, Long userId);
    /**
     * 用户根据ID查看应用详情
     * @param appId 应用ID
     * @param userId 用户ID
     * @return 应用VO
     */
    AppVO getAppById(Long appId, Long userId);
    /**
     * 用户分页查询自己的应用
     * @param request 查询请求体
     * @param userId 用户ID
     * @return 分页VO
     */
    Page<AppVO> listMyApps(AppQueryRequest request, Long userId);
    /**
     * 用户分页查询精选应用
     * @param request 查询请求体
     * @return 分页VO
     */
    Page<AppVO> listFeaturedApps(AppQueryRequest request);
    /**
     * 管理员删除任意应用
     * @param appId 应用ID
     * @return 是否删除成功
     */
    boolean adminDeleteApp(Long appId);
    /**
     * 管理员更新任意应用
     * @param request 更新请求体
     * @return 是否更新成功
     */
    boolean adminUpdateApp(AppAdminUpdateRequest request);
    /**
     * 管理员分页查询应用
     * @param request 查询请求体
     * @return 分页VO
     */
    Page<AppVO> adminListApps(AppAdminQueryRequest request);
    /**
     * 管理员根据ID查看应用详情
     * @param appId 应用ID
     * @return 应用VO
     */
    AppVO adminGetAppById(Long appId);
}
