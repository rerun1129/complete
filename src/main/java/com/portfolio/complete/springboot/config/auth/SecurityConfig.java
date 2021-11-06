package com.portfolio.complete.springboot.config.auth;

import com.portfolio.complete.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.userinfo.CustomUserTypesOAuth2UserService;


@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable().headers().frameOptions()
                .disable().and().authorizeRequests()
                .antMatchers("/", "/css/**" ,"/images/**","/js/**","/h2-console/**")
                .permitAll()
                .antMatchers("/api/v1/**")
                .hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/")
                .and().oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);  //이제 여기서 요구하는 클래스 타입의 인터페이스의 구현체를 만들면 컴파일 에러가 해소된다.

    }
}
