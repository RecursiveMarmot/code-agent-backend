package com.timess.codeagent.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.timess.codeagent.model.entity.App;
import com.timess.codeagent.service.AppService;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.timess.codeagent.model.dto.app.*;
import com.timess.codeagent.model.vo.AppVO;
import com.timess.codeagent.exception.*;
import com.timess.codeagent.model.entity.User;
import com.timess.codeagent.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import com.timess.codeagent.annotation.AuthCheck;
import com.timess.codeagent.constant.UserConstant;

/**
 * 应用 控制层。
 *
 * @author timess
 */
@RestController
@RequestMapping("/app")
public class AppController {
    @Autowired
    private AppService appService;
    @Resource
    private UserService userService;

    /**
     * 保存应用。
     *
     * @param app 应用
     * @return {@code true} 保存成功，{@code false} 保存失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody App app) {
        return appService.save(app);
    }

    /**
     * 根据主键删除应用。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Long id) {
        return appService.removeById(id);
    }

    /**
     * 根据主键更新应用。
     *
     * @param app 应用
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody App app) {
        return appService.updateById(app);
    }

    /**
     * 查询所有应用。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<App> list() {
        return appService.list();
    }

    /**
     * 根据主键获取应用。
     *
     * @param id 应用主键
     * @return 应用详情
     */
    @GetMapping("getInfo/{id}")
    public App getInfo(@PathVariable Long id) {
        return appService.getById(id);
    }

    /**
     * 分页查询应用。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<App> page(Page<App> page) {
        return appService.page(page);
    }

    /**
     * 用户创建应用。
     * @param request 创建请求体，包含应用名称和初始提示
     * @param httpRequest HTTP请求对象
     * @return 创建成功的应用ID
     */
    @PostMapping("/create")
    public BaseResponse<Long> createApp(@RequestBody AppCreateRequest request, HttpServletRequest httpRequest) {
        User loginUser = userService.getLoginUser(httpRequest);
        return ResultUtils.success(appService.createApp(request, loginUser.getId()));
    }

    /**
     * 用户修改自己的应用。
     * @param request 修改请求体，包含应用ID和新名称
     * @param httpRequest HTTP请求对象
     * @return 是否修改成功
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateApp(@RequestBody AppUpdateRequest request, HttpServletRequest httpRequest) {
        User loginUser = userService.getLoginUser(httpRequest);
        return ResultUtils.success(appService.updateApp(request, loginUser.getId()));
    }

    /**
     * 用户删除自己的应用。
     * @param id 应用ID
     * @param httpRequest HTTP请求对象
     * @return 是否删除成功
     */
    @PostMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteApp(@PathVariable Long id, HttpServletRequest httpRequest) {
        User loginUser = userService.getLoginUser(httpRequest);
        return ResultUtils.success(appService.deleteApp(id, loginUser.getId()));
    }

    /**
     * 用户查看应用详情。
     * @param id 应用ID
     * @param httpRequest HTTP请求对象
     * @return 应用详情VO
     */
    @GetMapping("/get/{id}")
    public BaseResponse<AppVO> getApp(@PathVariable Long id, HttpServletRequest httpRequest) {
        User loginUser = userService.getLoginUser(httpRequest);
        return ResultUtils.success(appService.getAppById(id, loginUser.getId()));
    }

    /**
     * 用户分页查询自己的应用。
     * @param request 查询请求体，包含名称、页码、页大小
     * @param httpRequest HTTP请求对象
     * @return 分页VO列表
     */
    @PostMapping("/my/list/page")
    public BaseResponse<Page<AppVO>> listMyApps(@RequestBody AppQueryRequest request, HttpServletRequest httpRequest) {
        User loginUser = userService.getLoginUser(httpRequest);
        return ResultUtils.success(appService.listMyApps(request, loginUser.getId()));
    }

    /**
     * 用户分页查询精选应用。
     * @param request 查询请求体，包含名称、页码、页大小
     * @return 分页VO列表
     */
    @PostMapping("/featured/list/page")
    public BaseResponse<Page<AppVO>> listFeaturedApps(@RequestBody AppQueryRequest request) {
        return ResultUtils.success(appService.listFeaturedApps(request));
    }

    /**
     * 管理员删除任意应用。
     * @param id 应用ID
     * @return 是否删除成功
     */
    @PostMapping("/admin/delete/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> adminDeleteApp(@PathVariable Long id) {
        return ResultUtils.success(appService.adminDeleteApp(id));
    }

    /**
     * 管理员更新任意应用。
     * @param request 更新请求体，包含应用ID、名称、封面、优先级
     * @return 是否更新成功
     */
    @PostMapping("/admin/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> adminUpdateApp(@RequestBody AppAdminUpdateRequest request) {
        return ResultUtils.success(appService.adminUpdateApp(request));
    }

    /**
     * 管理员分页查询应用。
     * @param request 查询请求体，支持多条件
     * @return 分页VO列表
     */
    @PostMapping("/admin/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<AppVO>> adminListApps(@RequestBody AppAdminQueryRequest request) {
        return ResultUtils.success(appService.adminListApps(request));
    }

    /**
     * 管理员查看应用详情。
     * @param id 应用ID
     * @return 应用详情VO
     */
    @GetMapping("/admin/get/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<AppVO> adminGetApp(@PathVariable Long id) {
        return ResultUtils.success(appService.adminGetAppById(id));
    }
}
