package com.test.bootsecurity.service;

import com.test.bootsecurity.dto.CustomUserDetails;
import com.test.bootsecurity.entity.Member;
import com.test.bootsecurity.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;


    //사용자 로그인 > username, password > loadUserByUsername 호출
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //select * from member where username = ?

       Member member = memberRepository.findByUsername(username);

       if(member != null){
           return new CustomUserDetails(member); // 로그인 성공
       }

        return null; // 로그인 실패
    }
}
