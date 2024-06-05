package com.test.jwt.config;

import com.test.jwt.auth.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //CSRF Disable
        //- 이 공격 방식은 세션 인증 방식에만 유효하다.
        //- JWT 방식에는 CSRF의 위험성이 거의 없다.
        http.csrf(auth -> auth.disable());

        //Form 인증 방식 사용 안함
        http.formLogin(auth -> auth.disable());

        //http basic 인증 방식 안함
        http.httpBasic(auth -> auth.disable());

        //세션 설정
        //- 기존의 세션 인증 방식을 사용하지 않겠다. > JWT 사용
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        //URI 허가
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "joinok").permitAll()
                .requestMatchers("/my").hasAnyRole("MEMBER", "ADMIN")
                .requestMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
        );



        //LoginFilter 등록
        //http.addFilter(new LoginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

}












