package com.bread.web;


import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.bread.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class,
    excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = SecurityConfig.class)
})
public class HelloControllerTest {


  //웹 API 테스트할 때 사용 -> HTTP METHOD 사용 가능
  @Autowired
  private MockMvc mvc;

  @WithMockUser(roles = "USER")
  @Test
  public void hello() throws Exception {
    String hello = "hello";

    mvc.perform(get("/hello")) // mvc에서 http get method로 ~/hello 요청
        .andExpect(status().isOk()) // andExpect() : 위 요청에 대한 예상, status.isOk() : http header의 status 검즘
        .andExpect(content().string(hello)); //  content() : 응답 바디를 검증
  }


  @WithMockUser(roles = "USER")
  @Test
  public void helloDto() throws Exception {
    String name = "hello";
    int amount = 1000;

    mvc.perform(get("/hello/dto")
                .param("name", name)
                .param("amount", String.valueOf(amount)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", equalTo(name))) // is() depreated로  equalTo()로 변경
        .andExpect(jsonPath("$.amount", equalTo(amount)));
  }

}