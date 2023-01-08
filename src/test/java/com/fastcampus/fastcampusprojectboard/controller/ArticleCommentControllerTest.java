package com.fastcampus.fastcampusprojectboard.controller;

import com.fastcampus.fastcampusprojectboard.config.SecurityConfig;
import com.fastcampus.fastcampusprojectboard.config.TestSecurityConfig;
import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.UserAccount;
import com.fastcampus.fastcampusprojectboard.dto.*;
import com.fastcampus.fastcampusprojectboard.dto.request.ArticleCommentRequest;
import com.fastcampus.fastcampusprojectboard.service.ArticleCommentService;
import com.fastcampus.fastcampusprojectboard.util.FormDataEncoder;
import org.jetbrains.annotations.Contract;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 댓글")
@Import({TestSecurityConfig.class, FormDataEncoder.class})
@WebMvcTest({ArticleCommentController.class})
class ArticleCommentControllerTest {

    private final MockMvc mvc;
    //@Autowired 필수적으로 써줘야 테스트에서는 동작한다.
    private final FormDataEncoder formDataEncoder;

    @MockBean private ArticleCommentService articleCommentService;

    @Contract(pure = true)
    ArticleCommentControllerTest(
            @Autowired MockMvc mvc,
            @Autowired FormDataEncoder formDataEncoder
    ) {
        this.mvc = mvc;
        this.formDataEncoder = formDataEncoder;

    }

    @WithUserDetails(value="unoTest", setupBefore= TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("Comment New")
    @Test
    void givenArticleComment_whenSaveArticleComment_thenReturnSaveComment() throws Exception {

        //Given
        long articleId = 1L;
        ArticleCommentRequest request = ArticleCommentRequest.of(articleId, "content");
        willDoNothing().given(articleCommentService).saveArticleComment(any(ArticleCommentDto.class));

        //When
        mvc.perform(
                        post("/comments/new")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(formDataEncoder.encode(request))
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles/" + articleId))
                .andExpect(redirectedUrl("/articles/"+articleId));

        //Then
        then(articleCommentService).should().saveArticleComment(any(ArticleCommentDto.class));

    }

    @WithUserDetails(value="unoTest", setupBefore= TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("Comment Delete")
    @Test
    void givenArticleId_whenDeleteArticleComment_thenReturnDeleteComment() throws Exception {

        //Given
        long commentId = 1L;
        long articleId = 1L;
        String userId = "unoTest";
        willDoNothing().given(articleCommentService).deleteArticleComment(commentId, userId);

        //When
        mvc.perform(
                        post("/comments/"+commentId+"/delete")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(formDataEncoder.encode(Map.of("articleId", articleId)))
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles/" + articleId))
                .andExpect(redirectedUrl("/articles/"+articleId));

        //Then
        then(articleCommentService).should().deleteArticleComment(commentId, userId);

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
                Set.of(HashtagDto.of("java"))
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
                "content"
        );
    }

    private ArticleDto createArticleDto() {
        return createArticleDto("title", "content", Set.of(HashtagDto.of("java")));
    }

    private ArticleDto createArticleDto(String title, String content, Set<HashtagDto> hashtagDtos) {
        return ArticleDto.of(1L,
                createUserAccountDto(),
                title,
                content,
                hashtagDtos,
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
                "Uno",
                "password",
                "uno@mail.com",
                "nickname",
                null
        );
    }
}