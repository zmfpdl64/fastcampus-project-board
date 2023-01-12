package com.fastcampus.fastcampusprojectboard.config;

import com.fastcampus.fastcampusprojectboard.domain.UserAccount;
import com.fastcampus.fastcampusprojectboard.dto.UserAccountDto;
import com.fastcampus.fastcampusprojectboard.repository.UserAccountRepository;
import com.fastcampus.fastcampusprojectboard.service.UserAccountService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {

    @MockBean private UserAccountService userAccountService;

    @BeforeTestMethod //각 테스트 코드가 실행되기 전에 메소드를 실행하는 어노테이션
    public void SecuritySetup() {
        given(userAccountService.searchUser(anyString()))
                .willReturn(Optional.of(createUserAccountDto()));
        given(userAccountService.saveUser(anyString(), anyString(), anyString(), anyString(), anyString()))
                .willReturn(createUserAccountDto());
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                "unoTest",
                "pw",
                "uno-test@email.com",
                "uno",
                "memo"
        );
    }

}
