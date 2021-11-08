package com.portfolio.complete1.springboot.config.auth.dto;

import com.portfolio.complete1.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * User 클래스에 이 클래스 없이 세션을 담으려고 하면 직렬화 오류가 발생한다.
 * 세션에 데이터를 담으려면 직렬화가 필요한데 지금까지는 JPA가 직렬화를 처리해줬기 때문에 신경을 쓰지 않아도 되었으나
 * 세션과 JPA는 다른 문제이기 때문에 세션을 사용할때는 직렬화가 필수다.
 * 그런데 엔티티에 직접 직렬화를 선언해버리면 자식 엔티티까지 직렬화 대상이 되어 버리기 때문에 예기치 않은
 * 사이드 이펙트와 성능 이슈가 발생할 수도 있다.
 * 그러니 세션을 만들때는 Dto를 하나 만들어서 거기서 세션정보를 받게 하자.
 */


@Getter
public class SessionUser implements Serializable {  //이미 인증된 사용자이기 때문에 이 정보들만 담아준다. 비밀번호 이런거 필요없음.

    private String name;
    private String email;
    private String picture;

    public SessionUser(User entity) {
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.picture = entity.getPicture();
    }
}
