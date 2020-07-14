package com.myapp.api.two.service;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/other")
public class OtherController {

  @PostMapping
  public ResponseEntity<Void> postOther(final HttpServletRequest request) {
    return ResponseEntity.noContent().build();
  }

}
