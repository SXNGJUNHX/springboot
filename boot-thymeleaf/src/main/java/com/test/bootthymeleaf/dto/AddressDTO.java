package com.test.bootthymeleaf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressDTO {

    private String seq;
    private String name;
    private String age;
    private String address;
    private String gender;

}
