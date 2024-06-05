package com.test.jwt.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        
        //사용자 > 로그인 시도 > 해당 메소드 호출
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        System.out.println("username >>>>>>>>>> " + username);

        //AuthenticationManager에게 username, password 전달 > DTO
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);


        return authenticationManager.authenticate(authRequest);
    }
    
    
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        //로그인 성공 > 메소드 호출
        System.out.println(">>>>>>>>>> 로그인 성공");

        super.successfulAuthentication(request, response, chain, authResult);
    }
    
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
       
        //로그인 실패 > 메소드 호출
        System.out.println(">>>>>>>>>> 로그인 실패");
        
        
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
