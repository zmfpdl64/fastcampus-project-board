package com.fastcampus.fastcampusprojectboard.service;

import com.fastcampus.fastcampusprojectboard.domain.ArticleComment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ArticleCommentService {

    @Transactional(readOnly = true)
    public List<ArticleComment> searchArticleComment(Long articleId) {
        return List.of();
    }
}
