package com.test.bootsecurity.dto;

import com.test.bootsecurity.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//인증 사용자 정보 객체
//- 구현 메서드 > 개인 관련 정보 제공 역할
public class CustomUserDetails implements UserDetails {

    //사용자 추가 정보 저장용
    //- 이전 수업(UserDTO)
    //- 현재 수업(Member Entity)
    private Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return member.getRole();
            }
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {

        //계정 만료?
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        //잠금 상태
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        //자격 증명(암호) 만료
        return true;
    }

    @Override
    public boolean isEnabled() {

        //사용 유무
        return true;
    }
}
