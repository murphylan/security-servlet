package com.academy.cn.securityservlet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.academy.cn.securityservlet.domain.SecurityUser;
import com.academy.cn.securityservlet.domain.User;
import com.academy.cn.securityservlet.repository.UserRepository;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {

  @Autowired
  private UserRepository userRepository;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/hello").hasAnyAuthority("ROLE_USER")
            .requestMatchers("/admin").hasAnyRole("ADMIN")
            .anyRequest().authenticated())
        .formLogin(Customizer.withDefaults())
        .rememberMe(Customizer.withDefaults());

    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> {
      // 根据用户名查询用户信息，这里假设使用userRepository来获取用户信息
      return userRepository
          .findByUsername(username)
          .map(SecurityUser::new)
          .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    };
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"));
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CommandLineRunner init(UserRepository userRepository, PasswordEncoder encoder) {
    return args -> {
      System.out.println("Initialization code here...");

      // 初始化用户数据
      User user1 = new User("user1@abc.com", encoder.encode("password"), "ROLE_USER");
      userRepository.save(user1);

      User user2 = new User("user2@abc.com", encoder.encode("password"), "ROLE_USER,ROLE_ADMIN");
      userRepository.save(user2);
    };
  }
}