package com.test.jwt.service;

import com.test.jwt.dto.MemberDTO;
import com.test.jwt.entity.Member;
import com.test.jwt.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(MemberDTO memberDTO) {

        Member member = Member.builder()
                .username(memberDTO.getUsername())
                .password(bCryptPasswordEncoder.encode(memberDTO.getPassword()))
                .role(memberDTO.getRole())
                .build();

        memberRepository.save(member);
    }
}
