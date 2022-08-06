package com.fastcampus.fastcampusprojectboard.controller;

import com.fastcampus.fastcampusprojectboard.config.SecurityConfig;
import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.UserAccount;
import com.fastcampus.fastcampusprojectboard.domain.contant.SearchType;
import com.fastcampus.fastcampusprojectboard.dto.ArticleDto;
import com.fastcampus.fastcampusprojectboard.dto.ArticleWithCommentsDto;
import com.fastcampus.fastcampusprojectboard.dto.UserAccountDto;
import com.fastcampus.fastcampusprojectboard.service.ArticleService;
import com.fastcampus.fastcampusprojectboard.service.PagenationService;
import com.fastcampus.fastcampusprojectboard.util.FormDataEncoder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 게시글")
@Import({SecurityConfig.class, FormDataEncoder.class})
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    private final MockMvc mvc;
    //@Autowired 필수적으로 써줘야 테스트에서는 동작한다.
    private final FormDataEncoder formDataEncoder;

    @MockBean private ArticleService articleService;
    @MockBean private PagenationService pagenationService;

    ArticleControllerTest(
            @Autowired MockMvc mvc,
            @Autowired FormDataEncoder formDataEncoder
    ) {
        this.mvc = mvc;
        this.formDataEncoder = formDataEncoder;

    }

    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 정상호출")
    @Test
    void givenNothing_whenRequestArticles_thenReturnsArticlesView() throws Exception {
    //Given
        given(articleService.searchArticles(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());
        given(pagenationService.getPaginationBarNumbers(anyInt(),anyInt())).willReturn(List.of(0,1,2,3,4));


    //When
        mvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("paginationBarNumbers"));
    //Then
        then(articleService).should().searchArticles(eq(null), eq(null), any(Pageable.class));
        then(pagenationService).should().getPaginationBarNumbers(anyInt(),anyInt());

    }

    @DisplayName("[view] [GET] 게시글 리스트 (게시판) 페이지 - 페이징, 정렬 기능")
    @Test
    public void givenNothing_whenRequestingArticlesView_thenReturnsArticles() throws Exception {


        //Given
        String sortName = "title";
        String direction = "desc";
        int pageNumber = 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.desc(sortName)));
        List<Integer> barNumbers = List.of(1,2,3,4,5);

        given(articleService.searchArticles(null, null, pageable)).willReturn(Page.empty());
        given(pagenationService.getPaginationBarNumbers(pageable.getPageNumber(), Page.empty().getTotalPages())).willReturn(barNumbers);


        //When'
        mvc.perform(get("/articles")
                .queryParam("page", String.valueOf(pageNumber))
                .queryParam("size", String.valueOf(pageSize))
                .queryParam("sort", sortName+ "," + direction)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attribute("paginationBarNumbers", barNumbers));

        //Then
        then(articleService).should().searchArticles( null, null,pageable);
        then(pagenationService).should().getPaginationBarNumbers(pageable.getPageNumber(), Page.empty().getTotalPages());

    }


    @Disabled("구현 중")
    @DisplayName("[view][GET] 게시글 상세 페이지 - 정상호출")
    @Test
    void givenNothing_whenRequestArticle_thenReturnsArticleView() throws Exception {
        //Given
        Long id = 1L;
        given(articleService.getArticleWithComments(id)).willReturn(createArticleWithCommentsDto());

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
                .andExpect(view().name("articles/search"));


        //Then
    }
    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 검색어와 함께 정상호출")
    @Test
    void givenSearchKeyword_whenRequestSearchingArticleView_thenReturnsArticlesSearchView() throws Exception {
        //Given
        SearchType searchType = SearchType.TITLE;
        String searchValue = "title";
        given(articleService.searchArticles(eq(searchType),eq(searchValue), any(Pageable.class))).willReturn(Page.empty());

        //When
        mvc.perform(get("/articles")
                        .queryParam("searchType", searchType.name())
                        .queryParam("searchValue", searchValue)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("paginationBarNumbers"))
                .andExpect(model().attributeExists("searchTypes"));
        //Then
        then(articleService).should().searchArticles(eq(searchType),eq(searchValue), any(Pageable.class));


    }







    @DisplayName("[view][GET] 해시태그 검색 페이지 - 정상호출")
    @Test
    void givenNothing_whenRequestArticleSearchHashtagView_thenReturnsArticlesHashtagSearchView() throws Exception {
        //Given
        List<String> hashtags = List.of("#java", "#spring", "#boot");
        given(articleService.searchArticleViaHashtag(eq(null), any(Pageable.class))).willReturn(Page.empty());
        given(articleService.getHashtag()).willReturn(hashtags);
        given(pagenationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0,1,2,3,4));


        //When
        mvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/search-hashtag"))
                .andExpect(model().attribute("articles", Page.empty()))
                .andExpect(model().attribute("hashtags", hashtags))
                .andExpect(model().attributeExists("paginationBarNumbers"));
        //Then
        then(articleService).should().searchArticleViaHashtag(eq(null), any(Pageable.class));
        then(pagenationService).should().getPaginationBarNumbers(anyInt(), anyInt());
        then(articleService).should().getHashtag();
    }

    @DisplayName("[view][GET] 해시태그 검색 페이지 - 정상호출, 해시태그 입력")
    @Test
    void givenHashtag_whenRequestArticleSearchHashtagView_thenReturnsArticlesHashtagSearchView() throws Exception {
        //Given
        String hashtag = "#java";
        List<String> hashtags = List.of("#java", "#spring", "#boot");
        given(articleService.searchArticleViaHashtag(eq(hashtag), any(Pageable.class))).willReturn(Page.empty());
        given(articleService.getHashtag()).willReturn(hashtags);
        given(pagenationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0,1,2,3,4));

        //When
        mvc.perform(get("/articles/search-hashtag")
                        .queryParam("searchValue", hashtag)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
//                .andExpect(view().name("articles/search-hashtag"))
                .andExpect(model().attribute("articles", Page.empty()))
                .andExpect(model().attribute("hashtags", hashtags))
                .andExpect(model().attributeExists("searchType"))
                .andExpect(model().attributeExists("paginationBarNumbers"));
        //Then
        then(articleService).should().searchArticleViaHashtag(eq(hashtag), any(Pageable.class));
        then(articleService).should().getHashtag();
        then(pagenationService).should().getPaginationBarNumbers(anyInt(), anyInt());
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
                "Uno",
                "password",
                "uno@mail.com",
                "nickname",
                null
        );
    }

}
