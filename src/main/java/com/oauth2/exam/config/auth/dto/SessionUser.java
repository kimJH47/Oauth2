package com.oauth2.exam.config.auth.dto;

import com.oauth2.exam.domain.user.User;
import lombok.Getter;

import java.io.Serializable;


/**
 * 세션에 클래스를 직렬화가 되어여함
 * User 클래스는 앤티티이게 떄문에 직렬화 기능을 가진 세션 Dto SessionUser 를 만들어서 사용
 *
 */
@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String picture;
    public SessionUser(User  user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getEmail();
    }
}
