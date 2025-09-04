package cn.zwh.ymcc.config;

import cn.zwh.ymcc.domain.Login;
import cn.zwh.ymcc.domain.Permission;
import cn.zwh.ymcc.exception.GlobleBusinessException;
import cn.zwh.ymcc.service.ILoginService;
import cn.zwh.ymcc.service.IPermissionService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class LoginDetailsService implements UserDetailsService {

    @Autowired
    private ILoginService loginService;

    @Autowired
    private IPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查用户信息
        Wrapper<Login> wrapper=new EntityWrapper<>();
        wrapper.eq("username",username);
        Login login = loginService.selectOne(wrapper);

        if (login==null){
            throw new GlobleBusinessException("用户名错误！");
        }
        //用户对应的权限
        List<Permission> permissions = permissionService.queryPermissionByLoginId(login.getId());
        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        for (Permission permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission.getSn()));
        }

        Login login1 = new Login();
        login1.setId(login.getId());
        login1.setUsername(login.getUsername());

        User user = new User(
                JSONObject.toJSONString(login1),
                login.getPassword(),
                login.getEnabled(),
                login.getAccountNonExpired(),
                login.getCredentialsNonExpired(),
                login.getAccountNonLocked(),
                authorities
        );
        return user;
    }
}
