package com.oauth2.exam.config.auth;

import com.oauth2.exam.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@RequiredArgsConstructor
//시큐리티 활성화
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;


    //config 설정
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /**
         *      .csrf()
         *      .disable()
         *      .headers()
         *      .frameOptions()
         *      .disable()
         *      .and()
         *      h2 console 을 쓰기위한 설정
         *
         *      authorizeRequests()
         *      URL 별 관리설정하는 옵션의 시작점
         *      authorizeRequests 가 선언 되야지만 antMatch 사용가능
         *
         *      antMatcher()
         *      권한 관리 대상을 지정하는 옵션
         *      URL,HTTP 메서드별로 관리가능
         *      "/" 등 지정된 URL 들은 .permitAll()을 통해 전체 열람권한 부여
         *      "/api/v1/**" 주소를 가진 API 는 USER 권한을 가진사람만 가능
         *
         *      anyRequest()
         *      설정된 값 이외 나머지 URL 들을 나타냄
         *      authenticated() 를 추가하여 나머지 URL들은 모두 인증된(로그인된) 사용자들에게만 허용
         *
         *      .logout().logoutSuccessUrl("/")
         *      로그아웃 기능에대한 여러 설정의 진입점
         *      로그아웃 성공시 "/" 로 이동
         *
         *      .oauth2Login()
         *      OAuth2 로그인 기능에대한 여러 설정의 진입점
         *
         *      .userInfoEndpoint()
         *      OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정을 담당
         *
         *      .userService()
         *      소셜로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록함
         *      리소스 서버(소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시가능
         *
         *
         */
        http.csrf()
            .disable()
            .headers()
            .frameOptions()
            .disable()
            .and()

            .authorizeRequests()
            .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**")
            .permitAll()
            .antMatchers("/api/v1/**")
            .hasRole(Role.USER.name())
            .anyRequest()
            .authenticated()
            .and()
            .logout()
            .logoutSuccessUrl("/")
            .and()
            .oauth2Login()
            .userInfoEndpoint()
            .userService(customOAuth2UserService);
    }

}
