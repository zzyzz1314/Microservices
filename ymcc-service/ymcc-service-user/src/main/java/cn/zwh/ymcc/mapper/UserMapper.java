package cn.zwh.ymcc.mapper;

import cn.zwh.ymcc.domain.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 会员登录账号 Mapper 接口
 * </p>
 *
 * @author whale
 * @since 2025-08-12
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from t_user")
    List<User> selectAll();
}
