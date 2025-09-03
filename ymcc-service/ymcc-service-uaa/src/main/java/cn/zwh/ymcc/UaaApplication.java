package cn.zwh.ymcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class})
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled= true)
public class UaaApplication {
    public static void main(String[] args) {
        SpringApplication.run(UaaApplication.class, args);
    }

}