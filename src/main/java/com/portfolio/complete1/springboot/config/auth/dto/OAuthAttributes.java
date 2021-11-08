package com.portfolio.complete1.springboot.config.auth.dto;


import com.portfolio.complete1.springboot.domain.user.Role;
import com.portfolio.complete1.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;



    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {  //registrationId은 로그인 유입을 구분하기 위해서 남겨둔 것임. 지금은 구글만 있어서 안씀.

        if ("naver".equals(registrationId)) {
            return ofNaver("id",attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);     //OAuth2User에서 반환하는 사용자 정보는 Map이기 때문에 값 하나하나를 변환하애 한다. 각 도메인마다 반환 유형을 메서드로 정의한다.
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes
                .builder()
                .name((String)attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes
                .builder()
                .name((String)response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }




    public User toEntity() {        //User의 엔티티를 생성, 가입 시에 기본권한이 있어야 하기 때문에 Role을 GUEST로 주었다.
        return User
                .builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }



}
