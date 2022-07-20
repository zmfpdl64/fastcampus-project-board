package com.fastcampus.fastcampusprojectboard.service;

import com.fastcampus.fastcampusprojectboard.domain.ArticleComment;
import com.fastcampus.fastcampusprojectboard.dto.ArticleCommentDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ArticleCommentService {

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComments(Long articleId) {
        return List.of();
    }
    public void saveArticleComment(ArticleCommentDto dto) {

    }
    public void updateArticleComment(ArticleCommentDto dto) {
    }

    public void deleteArticleComment(Long articleCommentId) {
    }
}
