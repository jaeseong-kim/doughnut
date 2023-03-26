package com.bread.web.dto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class HelloResponseDtoTest {

  @Test
  public void lombok() {
    //given
    String name = "test";
    int amount = 100;
    //when
    HelloResponseDto dto = new HelloResponseDto(name, amount);

    //then
    assertThat(dto.getName()).isEqualTo(name);
    assertThat(dto.getAmount()).isEqualTo(amount);
  }

}