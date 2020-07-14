package com.myapp.api;


import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.myapp.api.app.service.AppController;
import javax.annotation.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(
  classes = {
    AppController.class
  },
  loader = AnnotationConfigWebContextLoader.class
)
@WebAppConfiguration
@ActiveProfiles("TEST")
public class TestConfig {

  protected MockMvc mockMvc;

  @Resource
  private WebApplicationContext wac;

  @BeforeEach
  public void setup() {
    this.mockMvc = webAppContextSetup(this.wac).build();
  }

}
