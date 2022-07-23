package com.fastcampus.fastcampusprojectboard.service;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;


@DisplayName("비즈니스 로직 - 페이지네이션")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = PagenationService.class)
class PagenationServiceTest {

    private final PagenationService sut;

    public PagenationServiceTest(@Autowired PagenationService pagenationService) {
        this.sut = pagenationService;
    }

    @DisplayName("현재 페이지 번호와 총 페이지 수를 주면, 페이징 바 리스트를 만들어준다.")
    @MethodSource
    @ParameterizedTest(name = "[{index}] {0}, {1} => {2}")
    void givenCurrentPageNumberAndTotalPageNumber_whenCalculating_thenReturnsPaginationBarNumbers(int currentPageNumber, int totalPages, List<Integer> expecte) {

        //Given


        //When
        List<Integer> actual = sut.getPaginationBarNumbers(currentPageNumber, totalPages);

        //Then
        assertThat(actual).isEqualTo(expecte);

    }

    static Stream<Arguments> givenCurrentPageNumberAndTotalPageNumber_whenCalculating_thenReturnsPaginationBarNumbers() {
        return Stream.of(
                arguments(0, 13, List.of(0,1,2,3,4)),
                arguments(1, 13, List.of(0,1,2,3,4)),
                arguments(2, 13, List.of(0,1,2,3,4)),
                arguments(3, 13, List.of(1,2,3,4,5)),
                arguments(4, 13, List.of(2,3,4,5,6)),
                arguments(5, 13, List.of(3,4,5,6,7)),
                arguments(9, 13, List.of(7,8,9,10,11)),
                arguments(10, 13, List.of(8,9,10,11,12)),
                arguments(10, 13, List.of(8,9,10,11,12))
        );

    };

    @DisplayName("현재 설정되어 있는 페이지네이션 바의 길이를 알려준다.")
    @Test
    void givenNothing_whenCalling_thenReturnsCurrentBarLength() {
        //Given

        //When
        int BarLength = sut.currentBarLength();

        //Then
        assertThat(BarLength).isEqualTo(5);

    }

}