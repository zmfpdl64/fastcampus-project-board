package com.fastcampus.fastcampusprojectboard.dto;

import com.fastcampus.fastcampusprojectboard.domain.UserAccount;

import java.time.LocalDateTime;

public record UserAccountDto(
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy,
        String userId,
        String userPassword,
        String email,
        String nickname,
        String memo
) {

    public static UserAccountDto of(LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy, String userId, String userPassword, String email, String nickname, String memo) {
        return new UserAccountDto(createdAt, createdBy, modifiedAt, modifiedBy, userId, userPassword, email, nickname, memo);
    }

    public static UserAccountDto from(UserAccount entity) {
        return new UserAccountDto(
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy(),
                entity.getUserId(),
                entity.getUserPassword(),
                entity.getEmail(),
                entity.getNickname(),
                entity.getMemo()
        );
    }

    public UserAccount toEntity() {
        return UserAccount.of(
                userId,
                userPassword,
                email,
                nickname,
                memo
        );
    }
}
