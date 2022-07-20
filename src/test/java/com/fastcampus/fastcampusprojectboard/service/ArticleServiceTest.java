package com.fastcampus.fastcampusprojectboard.service;

import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.type.SearchType;
import com.fastcampus.fastcampusprojectboard.dto.ArticleDto;
import com.fastcampus.fastcampusprojectboard.dto.UpdateArticleDto;
import com.fastcampus.fastcampusprojectboard.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks ArticleService sut;

    @Mock private ArticleRepository articleRepository;


    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
    @Test
    void givenSearchParameter_whenSearchingArticles_thenReturnsArticleList() {

        //Given

        //When
        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword"); //제목, 본문, ID, 닉네임, 해시태그

        //Then
        assertThat(articles).isNotNull();
        
    }

    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnsArticle() {

        //Given

        //When
        ArticleDto article = sut.searchArticle(1L);

        //Then
        assertThat(article).isNotNull();

    }
    @DisplayName("페이지를 요청하면, 페이지를 반환한다.")
    @Test
    void given_whenSearchingArticle_thenReturnsArticle() {

        //Given

        //When
        ArticleDto article = sut.searchArticle(1L);

        //Then
        assertThat(article).isNotNull();

    }
    @DisplayName("게시글 정보를 입력하면 게시글을 생성한다.")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSaveArticle() {

        //Given
        given(articleRepository.save(any(Article.class))).willReturn(null);

        //When
        sut.saveArticle(ArticleDto.of(LocalDateTime.now(), "woojin", "title", "content", "hashtag"));


        //Then
        then(articleRepository).should().save(any(Article.class));
    }
    @DisplayName("게시글의 ID와 수정 정보를 입력하면, 게시글을 수정한다.")
    @Test
    void givenArticleIdAndInfo_whenUpdateArticle_thenReturnUpdateArticle() {

        //Given
        given(articleRepository.save(any(Article.class))).willReturn(null);

        //When
        sut.updateArticle(1L, UpdateArticleDto.of("title", "content", "hashtag"));


        //Then
        then(articleRepository).should().save(any(Article.class));
    }

        @DisplayName("게시글의 ID를 입력하면, 게시글을 삭제한다.")
    @Test
    void givenArticleId_whenDeletingArticle_thenReturnDeleteArticle() {

        //Given
        willDoNothing().given(articleRepository).delete(any(Article.class));

        //When
        sut.deleteArticle(1L);

        //Then
        then(articleRepository).should().delete(any(Article.class));
    }






}