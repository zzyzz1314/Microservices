package cn.zwh.ymcc.mapper;

import cn.zwh.ymcc.domain.Permission;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author whale
 * @since 2025-08-12
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> queryPermissionByLoginId(Long id);
}
