package com.myapp.api.two.service;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TwoController {

  @PostMapping
  public ResponseEntity<Void> keep(final HttpServletRequest request) {
    return ResponseEntity.noContent().build();
  }

}
