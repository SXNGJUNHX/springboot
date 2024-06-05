package com.test.bootsecurity.controller;

import com.test.bootsecurity.dto.MemberDTO;
import com.test.bootsecurity.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value="/join")
    public String join(){

        return "join";
    }

    @PostMapping(value="/joinok")
    public String joinok(MemberDTO memberDTO){

        System.out.println("dto >>>>>>>>>>>>>> " + memberDTO);

        memberService.join(memberDTO);

        return "redirect:/login";
    }

    @GetMapping(value="/my")
    public String my(){

        //회원만

        return "my";
    }

}


















