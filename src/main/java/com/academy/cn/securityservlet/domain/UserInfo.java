package com.academy.cn.securityservlet.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "userinfo")
public class UserInfo {

  @Id
  @GeneratedValue
  private Long id;
  private String username;
  private String password;
  private String roles;

}