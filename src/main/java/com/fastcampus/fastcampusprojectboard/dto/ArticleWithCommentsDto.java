package com.fastcampus.fastcampusprojectboard.dto;

import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.ArticleComment;
import com.fastcampus.fastcampusprojectboard.domain.Hashtag;
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
        Set<HashtagDto> hashtagDtos
) {
    public static ArticleWithCommentsDto of(LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy, Long id, UserAccountDto userAccount, Set<ArticleCommentDto> articleCommentDtos, String title, String content, Set<HashtagDto> hashtagDtos) {
        return new ArticleWithCommentsDto(createdAt, createdBy, modifiedAt, modifiedBy, id, userAccount, articleCommentDtos, title, content, hashtagDtos);
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
                entity.getHashtags().stream()
                        .map(HashtagDto::from)
                        .collect(Collectors.toUnmodifiableSet())
        );

    }

}
