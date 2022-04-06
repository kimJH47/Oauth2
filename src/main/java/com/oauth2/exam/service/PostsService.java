package com.oauth2.exam.service;

import com.oauth2.exam.domain.posts.Posts;
import com.oauth2.exam.domain.posts.PostsRepository;
import com.oauth2.exam.web.dto.PostsListResponseDto;
import com.oauth2.exam.web.dto.PostsResponseDto;
import com.oauth2.exam.web.dto.PostsSaveRequestDto;
import com.oauth2.exam.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor

public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity())
                              .getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                                     .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }


    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts post = postsRepository.findById(id)
                                    .orElseThrow(() -> new IllegalArgumentException("해당 개시글이 없습니다. id=" + id));
        return new PostsResponseDto(post);

    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {

        return postsRepository.findAllDesc()
                              .stream()
                              .map(PostsListResponseDto::new)
                              .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                                     .orElseThrow(() -> new IllegalArgumentException("개시글이 존재하지 않습니다, id=" + id));

        postsRepository.delete(posts);
        //postsRepository.deleteById(id);

    }
}
