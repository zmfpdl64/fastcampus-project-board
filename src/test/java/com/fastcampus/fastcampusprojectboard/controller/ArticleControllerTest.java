package com.fastcampus.fastcampusprojectboard.controller;

import com.fastcampus.fastcampusprojectboard.config.SecurityConfig;
import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.UserAccount;
import com.fastcampus.fastcampusprojectboard.dto.ArticleDto;
import com.fastcampus.fastcampusprojectboard.dto.ArticleWithCommentsDto;
import com.fastcampus.fastcampusprojectboard.dto.UserAccountDto;
import com.fastcampus.fastcampusprojectboard.service.ArticleService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 게시글")
@Import(SecurityConfig.class)
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    private final MockMvc mvc;
    //@Autowired 필수적으로 써줘야 테스트에서는 동작한다.

    @MockBean private ArticleService articleService;

    ArticleControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 정상호출")
    @Test
    void givenNothing_whenRequestArticles_thenReturnsArticlesView() throws Exception {
    //Given
        given(articleService.searchArticles(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());


    //When
        mvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"));
    //Then
        then(articleService).should().searchArticles(eq(null), eq(null), any(Pageable.class));

    }
    @Disabled("구현 중")
    @DisplayName("[view][GET] 게시글 상세 페이지 - 정상호출")
    @Test
    void givenNothing_whenRequestArticle_thenReturnsArticleView() throws Exception {
        //Given
        Long id = 1L;
        given(articleService.getArticle(id)).willReturn(createArticleWithCommentsDto());

        //When
        mvc.perform(get("/articles/"+id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("articleComments"));

        //Then
        then(articleService).should().getArticle(id);
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


    private ArticleWithCommentsDto createArticleWithCommentsDto() {
        return ArticleWithCommentsDto.of(
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno",
                1L,
                createUserAccountDto(),
                Set.of(),
                "title",
                "content",
                "hashtag"
        );

    }

    private UserAccount createUserAccount() {
        return UserAccount.of(
                "uno",
                "password",
                "uno@email.com",
                "Uno",
                null
        );
    }

    private Article createArticle() {
        return Article.of(
                createUserAccount(),
                "title",
                "content",
                "#java"
        );
    }

    private ArticleDto createArticleDto() {
        return createArticleDto("title", "content", "#java");
    }

    private ArticleDto createArticleDto(String title, String content, String hashtag) {
        return ArticleDto.of(1L,
                createUserAccountDto(),
                title,
                content,
                hashtag,
                LocalDateTime.now(),
                "Uno",
                LocalDateTime.now(),
                "Uno");
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno",
                1L,
                "Uno",
                "password",
                "uno@mail.com",
                "nickname",
                null
        );
    }

}
