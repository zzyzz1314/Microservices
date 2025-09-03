package cn.zwh.ymcc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

     //提供用户信息，这里没有从数据库查询用户信息，在内存中模拟
    //@Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager inMemoryUserDetailsManager =
        new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.withUsername("zs").password("123").authorities("admin").build());
        return inMemoryUserDetailsManager;
    }
  

    //密码编码器：不加密
    @Bean
    public PasswordEncoder passwordEncoder(){
        //密码加密
        return new BCryptPasswordEncoder();
    }

/*  //生成加迷密码
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("123"));
    }
    */

    //授权规则配置
    //.antMatchers("/admin/**").hasAuthority("admin")
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()//授权配置
                //.antMatchers("/login/list").hasAuthority("login:list")
                .antMatchers("/login").permitAll()  //登录路径放行
                .anyRequest().authenticated()                   //其他路径都要认证之后才能访问
                .and().formLogin()                              //允许表单登录
                .successForwardUrl("/loginSuccess")             // 设置登陆成功页
                .and().logout().permitAll()                    //登出路径放行 /logout
                .and().csrf().disable();                        //关闭跨域伪造检查
    }
}