package com.fastcampus.fastcampusprojectboard.controller;

import com.fastcampus.fastcampusprojectboard.config.SecurityConfig;
import com.fastcampus.fastcampusprojectboard.dto.ArticleCommentDto;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 게시글")
@Import({SecurityConfig.class, FormDataEncoder.class})
@WebMvcTest(ArticleCommentController.class)
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

    @DisplayName("Comment Delete")
    @Test
    void givenArticleId_whenDeleteArticleComment_thenReturnDeleteComment() throws Exception {

        //Given
        long articleId = 1L;
        willDoNothing().given(articleCommentService).deleteArticleComment(articleId);

        //When
        mvc.perform(
                        post("/comments/"+articleId+"/delete")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(formDataEncoder.encode(Map.of("articleId", articleId)))
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles/" + articleId))
                .andExpect(redirectedUrl("/articles/"+articleId));

        //Then
        then(articleCommentService).should().deleteArticleComment(articleId);

    }


}