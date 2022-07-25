package com.fastcampus.fastcampusprojectboard.domain.type;

import lombok.Getter;

public enum SearchType {
    CONTENT("내용"),
    HASHTAG("해시태그"),
    ID("유저 ID"),
    NICKNAME("닉네임"),
    TITLE("제목");

    @Getter private final String description;

    SearchType(String description) {
        this.description = description;
    }

}
