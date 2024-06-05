package com.test.bootsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {



    //암호 인코더
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }



    //대부분의 시큐리티 설정을 한다.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //URI 허가
        http.authorizeRequests(auth -> auth
//                .requestMatchers("/").permitAll()
//                .requestMatchers("/login").permitAll()
//                .requestMatchers("/loginok").permitAll()
                .requestMatchers("/", "/login").permitAll()
                .requestMatchers("/join", "/joinok").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN") // 특정 권한
                .requestMatchers("/my/**").hasAnyRole("MEMBER", "ADMIN")
                .anyRequest().authenticated() //나머지 경로 > 인증 사용자에게만 허가
        );

        //개발 시 > CRSF 비활성
        //http.csrf(auth -> auth.disable());

        //커스텀 로그인 페이지
        http.formLogin(auth -> auth
                .loginPage("/login") //로그인 폼 페이지 URL
                .loginProcessingUrl("/loginok").permitAll()
        );

        //로그아웃
        http.logout(auth -> auth.logoutUrl("/logout")
                                .logoutSuccessUrl("/")
        );

        return http.build();
    }

    
}





















