package com.fastcampus.fastcampusprojectboard.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditingFields {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)

    @CreatedDate
    @Column(nullable = false, updatable = false) protected LocalDateTime createdAt;    //생성시간

    @CreatedBy
    @Column(nullable = false, length = 100) protected String createdBy;           //생성자

    @LastModifiedDate
    @Column(nullable = false) protected LocalDateTime modifiedAt;   //수정시간

    @LastModifiedBy
    @Column(nullable = false, length = 100) protected String modifiedBy;          //수정자
}
