package com.fastcampus.fastcampusprojectboard.service;

import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.ArticleComment;
import com.fastcampus.fastcampusprojectboard.repository.ArticleCommentRepository;
import com.fastcampus.fastcampusprojectboard.repository.ArticleRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;


@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

    @InjectMocks ArticleCommentService sut;

    @Mock private ArticleCommentRepository articleCommentRepository;
    @Mock private ArticleRepository articleRepository;

    @DisplayName("게시글 id로 해당 댓글들 조회")
    @Test
    void givenArticleId_whenSearchingArticleComments_thenReturnComments() {

        //Given
        Long articleId = 1L;

        given(articleRepository.findById(articleId)).willReturn(Optional.of(Article.of("title", "content", "#java")));

        //When
        List<ArticleComment> articleComments = sut.searchArticleComment(articleId);

        //Then
        assertThat(articleComments).isNotNull();
        then(articleRepository).should().findById(articleId);

    }
    @DisplayName("댓글 정보를 전달하면 저장한다.")
    @Test
    void givenCommentContent_whenSaveComment_thenReturnSaveComment() {

        //Given
        Long articleId = 1L;

        given(articleRepository.findById(articleId)).willReturn(Optional.of(Article.of("title", "content", "#java")));

        //When
        List<ArticleComment> articleComments = sut.searchArticleComment(articleId);

        //Then
        assertThat(articleComments).isNotNull();
        then(articleRepository).should().findById(articleId);

    }




}