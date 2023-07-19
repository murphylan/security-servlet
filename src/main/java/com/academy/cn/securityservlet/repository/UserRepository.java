package com.academy.cn.securityservlet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academy.cn.securityservlet.domain.UserInfo;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

  Optional<UserInfo> findByUsername(String username);

}