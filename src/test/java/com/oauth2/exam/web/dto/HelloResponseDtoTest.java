package com.oauth2.exam.web.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class HelloResponseDtoTest {


    @Test
    public void loombokTest() throws Exception {
        //given
        String name = "test";
        int amount = 1000;
        //when
        HelloResponseDto helloResponseDto = new HelloResponseDto(name, amount);


        //then
        assertThat(helloResponseDto.getAmount())
                .isEqualTo(1000);
        assertThat(helloResponseDto.getName())
                .isEqualTo("test");
    }

}