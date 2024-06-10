package com.test.jwt.auth;

import com.test.jwt.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //사용자 > 로그인 시도 > 해당 메소드 호출

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        System.out.println("username >>>>>>>>>>>>>> " + username);

        //AuthenticationManager에게 username, password 전달 > DTO
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);


        return authenticationManager.authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        //로그인 성공 > 메소드 호출
        System.out.println(">>>>>>>>>>>>>> Login Success");

        //JWT 발급
        //- 인증 받은 유저의 정보 > username, role, 만료시간 > JWT 발급
        CustomUserDetails customUserDetails = (CustomUserDetails)authResult.getPrincipal();

        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority(); //ROLE_MEMEBER, ROLE_ADMIN

        String token = jwtUtil.createJwt(username, role, 1000 * 60 * 60L);

        //HTTP 헤더 필수 > RFC 7235 인증 규약
        //- Authorization 키
        //- Bearer 접두어 + 인증 토큰
        response.addHeader("Authorization", "Bearer " + token);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        //로그인 실패 > 메소드 호출
        System.out.println(">>>>>>>>>>>>>> Login Fail");

        response.setStatus(401);

    }
}
















