package com.cos.photogram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    BCryptPasswordEncoder Encode() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests()
                // 인증을 진행할 url 지정
                .antMatchers("/", "/user/**", "/image/**","/subscribe/**", "/comment/**").authenticated()
                // 이외 url 에 대해서는 허가
                .anyRequest().permitAll()
                .and()
                // 인증 요청이 들어올 경우에는 loginPage에 지정한 페이지로 이동시킨다.
                .formLogin()
                .loginPage("/auth/signin")
                // 인증 완료시 루트페이지로 이동
                .defaultSuccessUrl("/");

        return http.build();
    }
}
