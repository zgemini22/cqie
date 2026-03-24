package com.zds.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {

    @Value("${swagger.auth.username:zdsoft}")
    private String username;

    @Value("${swagger.auth.password:gxAft7fJ%t}")
    private String password;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        List<String> list = Arrays.asList("/doc.html",
                "/swagger-resources",
                "/swagger-resources/**",
                "/v2/api-docs",
                "/v2/api-docs/**");
        List<String> itemList = new ArrayList<>();
        for (ServiceEnum value : ServiceEnum.values()) {
            itemList.add("/" + value.getServiceName() + "/v2/api-docs");
            itemList.add("/" + value.getServiceName() + "/v2/api-docs/**");
        }
        itemList.addAll(list);
        String[] AUTH_REQUIRED = itemList.toArray(new String[list.size()]);
        return http
                .csrf().disable()
                .authorizeExchange()
                // 保护Swagger相关路径
                .pathMatchers(AUTH_REQUIRED).authenticated()
                // 其他所有请求放行
                .anyExchange().permitAll()
                .and()
                .httpBasic()
                .and()
                .formLogin().disable()
                .build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        // 注意：WebFlux中使用withDefaultPasswordEncoder，生产环境需要修改
        UserDetails user = User.withDefaultPasswordEncoder()
                .username(username)
                .password(password)
                .roles("ADMIN")
                .build();
        return new MapReactiveUserDetailsService(user);
    }
}
