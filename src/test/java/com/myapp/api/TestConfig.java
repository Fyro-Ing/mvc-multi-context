package com.myapp.api;


import com.myapp.api.app.Application;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(
  classes = Application.class,
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  properties = {
    "spring.main.allow-bean-definition-overriding=true"
  }
)
@ActiveProfiles("TEST")
@AutoConfigureMockMvc
public class TestConfig {

}
