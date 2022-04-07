package com.oauth2.exam.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class IndexControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void mainPage_로딩() throws Exception{
        //given
        String body = testRestTemplate.getForObject("/", String.class);

        //when

        //then

        assertThat(body)
                  .contains("OAuth2.0 개시글");
    }

}