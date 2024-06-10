package com.test.jwt.auth;

import com.test.jwt.dto.CustomUserDetails;
import com.test.jwt.entity.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        //검증
        //1. request > Authorization 헤더 검색
        //- Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImhvbmciLCJyb2xlIjoiUk9MRV9NRU1CRVIiLCJpYXQiOjE3MTc5ODQ0MzksImV4cCI6MTcxNzk4ODAzOX0.ChAUZX4qq31bP6ZOANsAMIoDHuGugVTrXNY2GN7nWs0
        String authorization = request.getHeader("Authorization");

        if(authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("Token invalid..");
            filterChain.doFilter(request, response);
            return; //필터 종료
        }
        
        //Token 만료 확인
        String token = authorization.split(" ")[1];

        if(jwtUtil.isExpired(token)){
            System.out.println("Token is expired..");
            filterChain.doFilter(request, response);
            return;
        }
        
        //인증 세션 생성
        //- 사용 가능한 토큰이라고 판단 > 정보 획득 > 임시 세션 생성
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        //엔티티 생성
        Member member = Member.builder()
                .username(username)
                .password("temp..") //아무 의미 없는 값
                .role(role)
                .build();


        //UserDetails 객체 생성(이걸 Security가 사용해서 보안 처리를 하기 때문에)
        CustomUserDetails customUserDetails = new CustomUserDetails(member);
        
        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        
        
        //임시 세션에 인증 정보를 담기
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}

















