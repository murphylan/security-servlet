package com.academy.cn.securityservlet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
  @GetMapping("/hello")
  public String hello() {
    return "hello world";
  }

  @GetMapping("/admin")
  public String admin() {
    return "hello admin";
  }
}