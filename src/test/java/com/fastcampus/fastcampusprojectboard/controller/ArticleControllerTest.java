package com.fastcampus.fastcampusprojectboard.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 게시글")
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    private final MockMvc mvc;
    //@Autowired 필수적으로 써줘야 테스트에서는 동작한다.

    ArticleControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 정상호출")
    @Test
    void givenNothing_whenRequestArticles_thenReturnsArticlesView() throws Exception {
    //Given

    //When
        mvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"));

    //Then
    }
    @Disabled("구현 중")

    @DisplayName("[view][GET] 게시글 상세 페이지 - 정상호출")
    @Test
    void givenNothing_whenRequestArticle_thenReturnsArticleView() throws Exception {
        //Given

        //When
        mvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("articleComments"));

        //Then
    }
    @Disabled("구현 중")

    @DisplayName("[view][GET] 게시글 검색 전용 페이지 - 정상호출")
    @Test
    void givenNothing_whenRequestArticleSearchView_thenReturnsArticlesSearchView() throws Exception {
        //Given

        //When
        mvc.perform(get("/articles/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles/search"));


        //Then
    }
    @Disabled("구현 중")

    @DisplayName("[view][GET] 해시태그 검색 페이지 - 정상호출")
    @Test
    void givenNothing_whenRequestArticleHashtagSearchView_thenReturnsArticlesHashtagSearchView() throws Exception {
        //Given

        //When
        mvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles/search-hashtag"));


        //Then
    }
}
