package com.test.oauth2.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {

    private String username;
    private String name;
    private String role;
}
