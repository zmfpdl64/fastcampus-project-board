package com.fastcampus.fastcampusprojectboard.repository;

import com.fastcampus.fastcampusprojectboard.config.JpaConfig;
import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ActiveProfiles("testdb")
class JpaRepositoryTest {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;


    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository,
            @Autowired UserAccountRepository userAccountRepository
    ) {

        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @DisplayName("select Test")
    @Test
    void givenTestData_whenselectting_thenWorksFine() {
//        Given


//        When
        List<Article> articles = articleRepository.findAll(); //#1


        //Then
        assertThat(articles).isNotNull().hasSize(123);     //#1


    }
    @DisplayName("insert Test")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
//        Given
        long previousCount = articleRepository.count(); //#2
        UserAccount userAccount1 = UserAccount.of("uno", "pw", null, null, null);
        UserAccount userAccount = userAccountRepository.save(userAccount1);
        Article article = Article.of(userAccount, "new article", "new content", "#spring");

//        When
        articleRepository.save(article);


        //Then
        assertThat(articleRepository.count())
                .isEqualTo(previousCount +1);   //#2
    }
    @DisplayName("update Test")
    @Test
    void givenTestData_whenUpdateing_thenWorksFine() {
//        Given
        Article article  = articleRepository.findById(1L).orElseThrow(); //#3
        String updatedHashtag = "#springboot";  //#3
        article.setHashtag(updatedHashtag);     //#3

//        When
        Article article1 = articleRepository.saveAndFlush(article); //#3    saveandflush를 사용하면 query를 관찰할 수 있으며 db에 반영된다.

        //Then
        assertThat(article1).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);    //#3
        assertThat(article1.getHashtag()).isEqualTo(updatedHashtag);                        //#3
    }
    @DisplayName("delete Test")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
//        Given
        long previousCount = articleRepository.count();
        Article article  = articleRepository.findById(1L).orElseThrow(); //#4
        long previousArticleComment = articleCommentRepository.count();

        int deletedCommentSize = article.getArticleComments().size();

//        When
        articleRepository.delete(article);

        //Then
        assertThat(articleRepository.count()).isEqualTo(previousCount-1);                        //#4
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleComment - deletedCommentSize);                        //#4
    }
}