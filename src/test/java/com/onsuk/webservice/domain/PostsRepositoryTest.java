package com.onsuk.webservice.domain;


import com.onsuk.webservice.domain.posts.Posts;
import com.onsuk.webservice.domain.posts.PostsRepository;
import javafx.geometry.Pos;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup() {
        /*
        이후 테스트 코드에 영향을 끼치지 않기 위해
        테스트 메소드가 끝날 때마다 respository 전체를 비우는 코드
        */
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //given
        postsRepository.save(Posts.builder()
            .title("테스트 게시글")
            .content("테스트 본문")
            .author("ondaesuk93@gmail.com")
            .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        Assert.assertThat(posts.getTitle(), is("테스트 게시글"));
        Assert.assertThat(posts.getContent(), is("테스트 본문"));
    }

    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.now();
        postsRepository.save(Posts.builder()
            .title("테스트 게시글")
            .content("테스트 본문")
            .author("ondaesuk93@gmail.com")
            .build());
        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        Assert.assertTrue(posts.getCreatedDate().isAfter(now));
        Assert.assertTrue(posts.getModifiedDate().isAfter(now));
    }
}
