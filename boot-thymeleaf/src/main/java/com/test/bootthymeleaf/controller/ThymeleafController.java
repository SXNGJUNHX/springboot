package com.test.bootthymeleaf.controller;

import com.test.bootthymeleaf.dto.AddressDTO;
import com.test.bootthymeleaf.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class ThymeleafController {

    private final AddressMapper addressMapper;
    private final MessageSource messageSource;

    @Autowired
    public ThymeleafController(AddressMapper addressMapper, MessageSource messageSource) {
        this.addressMapper = addressMapper;
        this.messageSource = messageSource;
    }

    @GetMapping(value = "/m01.do")
    public String m01(Model model) {

        //단일값 전달
        int num = addressMapper.num();
        String txt = addressMapper.txt();

        //객체 전달(map, dto)
        AddressDTO dto = addressMapper.get();

        Map<String, Integer> map = new HashMap<>();
        map.put("kor", 100);
        map.put("eng", 90);
        map.put("mat", 80);

        model.addAttribute("num", num);
        model.addAttribute("txt", txt);
        model.addAttribute("dto", dto);
        model.addAttribute("map", map);

        return "m01";
    }

    @GetMapping(value = "/m02.do")
    public String m02(Model model) {

        //스프링 메시지, Spring Message
        //- 프로젝트내에서 자주 사용되는(반복적인) 문자열
        //- 다국어 지원

        //- src/main/resources > "messages.properties"
        //- src/main/resources > "messages_en.properties"
        //- src/main/resources > "messages_ja.properties"

        //스프링 메시지
        System.out.println(messageSource.getMessage("language", null, Locale.JAPAN));
        System.out.println(messageSource.getMessage("item.desc", new Object[] { "하나", "둘"}, Locale.JAPAN));

        return "m02.html";
    }

    @GetMapping(value = "/m03.do")
    public String m03(Model model) {

        //- 연산자
        int a = 10;
        int b = 3;

        model.addAttribute("a", a);
        model.addAttribute("b", b);

        return "m03";
    }

    @GetMapping(value = "/m04.do")
    public String m04(Model model) {

        //HTML 속성 조작
        model.addAttribute("num", addressMapper.num());
        model.addAttribute("txt", addressMapper.txt());
        model.addAttribute("color" , "cornflowerblue");

        return "m04";
    }

    @GetMapping(value = "/m05.do")
    public String m05(Model model) {

        //콘텐츠 조작(시작태그~끝태그 사이)
        //- PCDATA 조작
        //- 자식 태그 조작
        //- innerText(textContent)
        //- innerHTML

        String txt1 = "홍길동입니다.";
        String txt2 = "<b>홍길동</b>입니다.";


        int num = 100;
        String name = "홍길동";
        AddressDTO dto = addressMapper.get();
        Map<String, Integer> map = new HashMap<>();
        map.put("kor", 100);
        map.put("eng", 90);
        map.put("mat", 80);
        List<String> names = addressMapper.names();
        List<AddressDTO> list = addressMapper.list();

        model.addAttribute("num", num);
        model.addAttribute("dto", dto);
        model.addAttribute("map", map);
        model.addAttribute("names", names);
        model.addAttribute("list", list);

        model.addAttribute("txt1", txt1);
        model.addAttribute("txt2", txt2);
        model.addAttribute("name", name);

        return "m05";
    }

    @GetMapping(value = "/m06.do")
    public String m06(Model model) {

        //포맷 츨력(printf)
        int num1 = 1234567;
        double num2 = 12345.6789;
        Calendar now = Calendar.getInstance();

        model.addAttribute("num1", num1);
        model.addAttribute("num2", num2);
        model.addAttribute("now", now);

        return "m06";
    }

    @GetMapping(value = "/m07.do")
    public String m07(Model model) {

        //Link URL Expressions, 링크 주소 표현식
        //- @{}
        //- 링크의 URL을 출력하는 역할
        //- 매개변수 처리 용이(QueryString)

        int seq = 10;
        String mode = "add";

        model.addAttribute("seq", seq);
        model.addAttribute("mode", mode);


        return "m07";
    }

    @GetMapping(value = "/m08.do")
    public String m08(Model model) {

        //제어문
        int num1 = 10;
        int num2 = -5;
        String mode = "add";


        List<String> names = addressMapper.names();
        List<AddressDTO> list = addressMapper.list();


        model.addAttribute("num1", num1);
        model.addAttribute("num2", num2);
        model.addAttribute("mode", mode);

        model.addAttribute("names", names);
        model.addAttribute("list", list);

        return "m08";
    }

    /*
    @GetMapping(value = "/m.do")
    public String m(Model model) {

        return "m";
    }
    */


}




















