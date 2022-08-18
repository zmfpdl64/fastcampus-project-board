package com.fastcampus.fastcampusprojectboard.config;

import com.fastcampus.fastcampusprojectboard.domain.UserAccount;
import com.fastcampus.fastcampusprojectboard.repository.UserAccountRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {

    @MockBean private UserAccountRepository userAccountRepository;

    @BeforeTestMethod //각 테스트 코드가 실행되기 전에 메소드를 실행하는 어노테이션
    public void SecuritySetup() {
        given(userAccountRepository.findById(anyString())).willReturn(Optional.of(UserAccount.of(
                "unoTest",
                "pw",
                "uno-test@email.com",
                "uno",
                "memo"
        )));



    }

}
