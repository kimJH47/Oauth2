package com.oauth2.exam.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;



public interface PostsRepository extends JpaRepository<Posts,Long> {

}
