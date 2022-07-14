package com.fastcampus.fastcampusprojectboard;

import com.fastcampus.fastcampusprojectboard.repository.ArticleCommentRepository;
import com.fastcampus.fastcampusprojectboard.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import javax.transaction.Transactional;

@SpringBootTest
@Import(JpaConfig.class)
@DisplayName("main")
class FastcampusProjectBoardApplicationTests {

	private final ArticleRepository articleRepository;
	private final  ArticleCommentRepository articleCommentRepository;

	FastcampusProjectBoardApplicationTests(
			@Autowired ArticleRepository articleRepository,
			@Autowired ArticleCommentRepository articleCommentRepository) {
		this.articleRepository = articleRepository;
		this.articleCommentRepository = articleCommentRepository;
	}


	@Test
	@Transactional
	void contextLoads() {

	}

}
