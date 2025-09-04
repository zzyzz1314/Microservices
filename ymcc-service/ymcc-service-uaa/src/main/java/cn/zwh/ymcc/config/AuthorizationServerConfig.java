package cn.zwh.ymcc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.Arrays;

//授权服务配置
@Configuration
//开启授权服务配置
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**======================================================================================
     * 描述：1.客户端详情配置
     ======================================================================================*/

    //JWT令牌秘钥
    public static final String SIGN_KEY = "123";

    //数据源
    @Autowired
    private DataSource dataSource ;

    //编码器
    @Autowired
    private PasswordEncoder passwordEncoder;

    //1.1.定义ClientDetailsService 客户端详情配置服务
    @Bean
    public ClientDetailsService clientDetailsService(){
        //JdbcClientDetailsService的作用是去数据库加载客户端配置，加载表 oauth_client_details
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        jdbcClientDetailsService.setPasswordEncoder(passwordEncoder);
        return jdbcClientDetailsService;
    }

    //1.2.配置客户端详情
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //ClientDetailsService:就是提供客户端详情配置的一个服务
        clients.withClientDetails(clientDetailsService());
    }

    /**======================================================================================
     * 方法描述：2.服务端点：授权码，令牌管理配置
     ======================================================================================*/

    //2.1.认证管理器，密码授权模式需要
    @Autowired
    private AuthenticationManager authenticationManager;

    //2.2.定义 JdbcAuthorizationCodeServices 授权码的服务，基于数据库存储
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    //2.3.定义AuthorizationServerTokenServices ，令牌的服务配置
    @Bean
    public AuthorizationServerTokenServices tokenService(){
        //创建默认的令牌服务
        DefaultTokenServices services = new DefaultTokenServices();
        //指定客户端详情配置
        services.setClientDetailsService(clientDetailsService());
        //支持产生刷新token
        services.setSupportRefreshToken(true);
        //token存储方式
        services.setTokenStore(tokenStore());
        //设置token增强 - 设置token转换器
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter()));
        services.setTokenEnhancer(tokenEnhancerChain);

        //token有效时间
        services.setAccessTokenValiditySeconds(72000);
        //刷新令牌默认有效时间
        services.setRefreshTokenValiditySeconds(72000);
        return services;
    }

    //2.4.配置令牌的存储
    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    //2.5.JWT令牌校验工具
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        //JWT的令牌转换工具
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //设置JWT签名密钥。它可以是简单的MAC密钥，也可以是RSA密钥
        //设置非对称秘钥使用：jwtAccessTokenConverter.setKeyPair(keyPair());
        jwtAccessTokenConverter.setSigningKey(SIGN_KEY);

        return jwtAccessTokenConverter;
    }

    //2.6.配置授权码和令牌端点服务
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
            //密码授权模式需要
            .authenticationManager(authenticationManager)
            //授权码模式服务
            .authorizationCodeServices(authorizationCodeServices())
            //配置令牌管理服务
            .tokenServices(tokenService())
            //允许post方式请求
            .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    /**======================================================================================
     * 方法描述：3.授权服务安全配置，url是否放行等
     ======================================================================================*/
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
            //对应/oauth/check_token ，路径公开
            .checkTokenAccess("permitAll()")
            //允许客户端进行表单身份验证,使用表单认证申请令牌
            .allowFormAuthenticationForClients();
    }
}