package com.myapp.api.one.service;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OneController {

  @PostMapping
  public ResponseEntity<Void> post(final HttpServletRequest request) {
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<Void> get(final HttpServletRequest request) {
    return ResponseEntity.noContent().build();
  }

}
