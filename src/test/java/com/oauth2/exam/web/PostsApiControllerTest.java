package com.oauth2.exam.web;

import com.oauth2.exam.domain.posts.Posts;
import com.oauth2.exam.domain.posts.PostsRepository;
import com.oauth2.exam.web.dto.PostsSaveRequestDto;
import com.oauth2.exam.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Test
    public void registerTest() throws Exception {
        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                                                            .title(title)
                                                            .content(content)
                                                            .author("kmr2644@gmail.com")
                                                            .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        System.out.println("url = " + url);
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Posts> list = postsRepository.findAll();
        assertThat(list.get(0)
                       .getTitle()).isEqualTo(title);
        assertThat(list.get(0)
                       .getContent()).isEqualTo(content);
    }

    @Test
    public void postsDeleteTest() throws Exception {

        //given
        String title = "title";
        String content = "content";
        Posts save = postsRepository.save(Posts.builder()
                                               .title(title)
                                               .content(content)
                                               .author("kmr")
                                               .build());
        Long id = save.getId();
        String url = "http://localhost:" + port + "/api/v1/posts/" + save.getId();
        HttpEntity<Posts> postsHttpEntity = new HttpEntity<>(save);
        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, postsHttpEntity, Long.class);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThanOrEqualTo(0L);

        List<Posts> list = postsRepository.findAll();

        assertThat(list).isEmpty();
    }
    @Test
    public void modifiedTest() throws Exception {
        String title = "title";
        String content = "content";
        Posts save = postsRepository.save(Posts.builder()
                                               .title(title)
                                               .content(content)
                                               .author("kmr")
                                               .build());


        PostsUpdateRequestDto updateRequestDto = PostsUpdateRequestDto.builder()
                                                                      .title("updateTitle")
                                                                      .content("Update Content")
                                                                      .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + save.getId();
        System.out.println("url = " + url);
        HttpEntity<PostsUpdateRequestDto> requestDtoHttpEntity = new HttpEntity<>(updateRequestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestDtoHttpEntity, Long.class);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThanOrEqualTo(0L);

        List<Posts> list = postsRepository.findAll();

        assertThat(list.get(0)
                       .getTitle()).isEqualTo(updateRequestDto.getTitle());
        assertThat(list.get(0)
                       .getContent()).isEqualTo(updateRequestDto.getContent());


    }
}