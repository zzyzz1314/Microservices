package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.domain.Permission;
import cn.zwh.ymcc.mapper.PermissionMapper;
import cn.zwh.ymcc.service.IPermissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author whale
 * @since 2025-08-12
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
