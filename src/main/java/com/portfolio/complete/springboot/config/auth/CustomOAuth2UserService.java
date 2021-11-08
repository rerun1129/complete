package com.portfolio.complete.springboot.config.auth;

import com.portfolio.complete.springboot.config.auth.dto.OAuthAttributes;
import com.portfolio.complete.springboot.config.auth.dto.SessionUser;
import com.portfolio.complete.springboot.domain.user.User;
import com.portfolio.complete.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;


@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();    //현재 로그인 진횅 중인 서비스를 구분하는 코드, 차후에 구글 이외의 연동 추가 시에 구분하기 위함

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();       //로그인 진행 시 키가 되는 필드값 PK와 같은 의미 이후에 네이버와 구글 로그인 동시 지원시 사용

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());     //OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스(컨트롤러로 화면에 쏴줄거임)

        User user = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(user));        //세션에 사용자 정보를 저장하기 위한 Dto 클래스

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),attributes.getAttributes(),attributes.getNameAttributeKey());

    }

    private User saveOrUpdate(OAuthAttributes attributes) {

        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());     //흐름을 보면 일단 이메일을 찾은 다음에 그것을 바탕으로 map 에 이름과 사진을 담아주고 만약에 등록된 이메일이 없으면 어트리뷰트를 엔티티에 담는다.

        return userRepository.save(user); //그리고 엔티티에 담긴 정보가 저장소에 있었는지 없었는지 상관없이 user 정보를 가지고 DB에 save 를 때려버린다. 그리고 save 메서드의 구조가 persist 하거나 merge 하거나 둘 중에 하나를 하기 때문에
                                            //특별히 merge 를 해서 원치 않는 결과가 나올 상황이 아니면 그냥 save 를 그대로 써도 된다.
    }


}
