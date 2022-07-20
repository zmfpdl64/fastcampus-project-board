package com.fastcampus.fastcampusprojectboard.dto;

import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.ArticleComment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleWithCommentsDto(
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy,
        Long id,
        UserAccountDto userAccount,
        Set<ArticleCommentDto> articleCommentDtos,
        String title,
        String content,
        String hashtag
) {
    public static ArticleWithCommentsDto of(LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy, Long id, UserAccountDto userAccount, Set<ArticleCommentDto> articleCommentDtos, String title, String content, String hashtag) {
        return new ArticleWithCommentsDto(createdAt, createdBy, modifiedAt, modifiedBy, id, userAccount, articleCommentDtos, title, content, hashtag);
    }

    public static ArticleWithCommentsDto from(Article entity) {
        return new ArticleWithCommentsDto(
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy(),
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getArticleComments().stream()
                        .map(ArticleCommentDto::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag()
        );

    }

}
