package com.myapp.api.two.service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.myapp.api.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public class TwoControllerTest extends TestConfig {

  @Test
  public void test() throws Exception {
    final MockHttpServletRequestBuilder request = post("/");
    this.mockMvc.perform(request)
      .andExpect(status().isNoContent())
      .andReturn();
  }

}
