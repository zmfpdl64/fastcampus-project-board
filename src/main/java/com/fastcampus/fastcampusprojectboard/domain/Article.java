package com.fastcampus.fastcampusprojectboard.domain;

import java.time.LocalDateTime;

public class Article {
    private Long id;
    private String title;   //제목
    private String hashtag; //해시태그

    private LocalDateTime createdAt;    //생성시간
    private String createdBy;           //생성자
    private LocalDateTime modifiedAt;   //수정시간
    private String modifiedBy;          //수정자

}
