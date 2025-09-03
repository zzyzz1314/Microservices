package cn.zwh.ymcc.service;

import cn.zwh.ymcc.domain.Permission;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author whale
 * @since 2025-08-12
 */
public interface IPermissionService extends IService<Permission> {

    /**
     * 根据登录id查询权限
     * @param id
     * @return
     */
    List<Permission> queryPermissionByLoginId(Long id);
}
