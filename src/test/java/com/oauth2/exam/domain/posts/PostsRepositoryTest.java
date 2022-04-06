package com.oauth2.exam.domain.posts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

//    @AfterEach
//    public void after() {
//        postsRepository.deleteAll();
//    }

    @Test
    public void 개시글저장_불러오기() throws Exception {
        //given
        String title = "title";
        String content = "in content";

        postsRepository.save(Posts.builder()
                                  .title(title)
                                  .content(content)
                                  .author("kmr2644@gmail.com")
                                  .build());
        //when
        List<Posts> postsList = postsRepository.findAll();
        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntityTest_등록() {
        LocalDateTime localDateTime = LocalDateTime.of(2020, 4, 8, 0, 0, 0);
        String title = "title";
        String content = "in content";
        postsRepository.save(Posts.builder()
                                  .title(title)
                                  .content(content)
                                  .author("kmr2644@gmail.com")
                                  .build());
        //when
        List<Posts> all = postsRepository.findAll();
        Posts posts = all.get(0);

        System.out.println("생성시간:"+posts.getCreateTime());
        System.out.println("생성시간:" + posts.getModifiedTime());

        assertThat(posts.getCreateTime()).isAfter(localDateTime);
        assertThat(posts.getModifiedTime()).isAfter(localDateTime);

    }
}