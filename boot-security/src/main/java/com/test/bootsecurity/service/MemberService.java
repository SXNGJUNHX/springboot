package com.test.bootsecurity.service;

import com.test.bootsecurity.dto.MemberDTO;
import com.test.bootsecurity.entity.Member;
import com.test.bootsecurity.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public void join(MemberDTO memberDTO) {

        //아이디 중복검사
        boolean result = memberRepository.existsByUsername(memberDTO.getUsername());
        if (result) {
            return;
        }


        //DTO > Entity
        Member member = Member.builder()
                .username(memberDTO.getUsername())
                .password(bCryptPasswordEncoder.encode(memberDTO.getPassword()))
                .role(memberDTO.getRole())
                .build();

        memberRepository.save(member);

    }
}

















