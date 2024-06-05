package com.test.jwt.controller;

import com.test.jwt.dto.MemberDTO;
import com.test.jwt.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value="/my")
    public String my() {

        return "MemberController";
    }

    @PostMapping(value="/joinok")
    public String joinok(MemberDTO memberDTO) {

        System.out.println("dto >>>>>>>>>>>>> " + memberDTO);

        memberService.join(memberDTO);

        return "Join Success";
    }

}
