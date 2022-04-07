package com.oauth2.exam.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oauth2.exam.domain.posts.Posts;
import com.oauth2.exam.domain.posts.PostsRepository;
import com.oauth2.exam.web.dto.PostsSaveRequestDto;
import com.oauth2.exam.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class PostsApiControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                             .apply(springSecurity())
                             .build();
    }

    @AfterEach
    public void after() {
        postsRepository.deleteAll();

    }

    @Autowired
    private PostsRepository postsRepository;

    @Test
    @WithMockUser(roles = "USER")
    public void 등록() throws Exception {
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
        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_UTF8)
                             .content(new ObjectMapper().writeValueAsString(requestDto)))
           .andExpect(status().isOk());

        //then
        List<Posts> list = postsRepository.findAll();
        assertThat(list.get(0)
                       .getTitle()).isEqualTo(title);
        assertThat(list.get(0)
                       .getContent()).isEqualTo(content);

    }


    @Test
    @WithMockUser(roles = "USER")
    public void 수정() throws Exception {

        //given
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
        //HttpEntity<PostsUpdateRequestDto> requestDtoHttpEntity = new HttpEntity<>(updateRequestDto);

        //when
        mvc.perform(put(url).contentType(MediaType.APPLICATION_JSON_UTF8)
                             .content(new ObjectMapper().writeValueAsString(updateRequestDto)))
           .andExpect(status().isOk());

        //then
        List<Posts> list = postsRepository.findAll();

        assertThat(list.get(0)
                       .getTitle()).isEqualTo(updateRequestDto.getTitle());
        assertThat(list.get(0)
                       .getContent()).isEqualTo(updateRequestDto.getContent());


    }

    @Test
    @WithMockUser(roles = "USER")
    public void 삭제() throws Exception {

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
        //HttpEntity<Posts> postsHttpEntity = new HttpEntity<>(save);
        //when
        mvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON_UTF8)
                               .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk());
        //then
        List<Posts> list = postsRepository.findAll();
        assertThat(list).isEmpty();
    }
}



