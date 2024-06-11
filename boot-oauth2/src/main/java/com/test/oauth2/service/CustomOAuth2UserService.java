package com.test.oauth2.service;

import com.test.oauth2.dto.*;
import com.test.oauth2.entity.UserEntity;
import com.test.oauth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    //리소스 서버로부터 받아오는 개인 정보 > 네이버, 구글의 유저 정보가 넘어온다
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println("받아온 개인정보 >>>>>>> " + oAuth2User);

        //네이버 or 구글 확인
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        /*

        OAuth2Response
        - 소셜 제공자마다 반환 데이터의 구조가 다르다.
        - 공통 타입 설계 > 상속 > 각각의 구현체

        네이버 데이터
        {
            resultcode=00,
            message=success,
            response={
                id=1111,
                name=홍길동
            }
        }

        네이버 데이터
        {
            resultcode=00,
            message=success,
            id=1111,
            name=홍길동
        }

        */

        Oauth2Response oauth2Response = null;

        if(registrationId.equals("naver")){
            oauth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if(registrationId.equals("google")){
            oauth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        //유저 식별자 중복 문제
        String username = oauth2Response.getProvider() + " " + oauth2Response.getProviderId();

        UserDTO userDTO = new UserDTO();
        
        userDTO.setUsername(username);
        userDTO.setName(oauth2Response.getName());
        userDTO.setRole("ROLE_MEMBER");


        //인증 유저 > DB 저장
        UserEntity user = userRepository.findByUsername(username);

        if(user == null){

            UserEntity entity = UserEntity.builder()
                    .username(username)
                    .email(oauth2Response.getEmail())
                    .role("ROLE_MEMBER")
                    .build();

            userRepository.save(entity);

        } else {
            
            //프로바이더쪽에서 수정된 정보가 있다면.. > DB 반영
            userRepository.save(user);
            
        }


        return new CustomOAuth2User(userDTO);
    }
}
