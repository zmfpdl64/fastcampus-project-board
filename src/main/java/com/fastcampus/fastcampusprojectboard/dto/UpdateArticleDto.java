package com.fastcampus.fastcampusprojectboard.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record UpdateArticleDto(
        String title,
        String content,
        String hashtag
) {
    public static UpdateArticleDto of( String title, String content, String hashtag) {
        return new UpdateArticleDto(title, content, hashtag);
    }
}
