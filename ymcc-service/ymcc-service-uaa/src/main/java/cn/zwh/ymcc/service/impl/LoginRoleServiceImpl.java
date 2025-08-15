package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.domain.LoginRole;
import cn.zwh.ymcc.mapper.LoginRoleMapper;
import cn.zwh.ymcc.service.ILoginRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和角色中间表 服务实现类
 * </p>
 *
 * @author whale
 * @since 2025-08-12
 */
@Service
public class LoginRoleServiceImpl extends ServiceImpl<LoginRoleMapper, LoginRole> implements ILoginRoleService {

}
