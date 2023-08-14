package com.academy.cn.securityservlet.controller;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @Autowired
  JwtEncoder encoder;

  @PostMapping("/token")
  public Map<String, String> token(Authentication authentication) {
    Instant now = Instant.now();
    long expiry = 36000L;
    // @formatter:off
		String scope = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(" "));
		JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(now)
				.expiresAt(now.plusSeconds(expiry))
				.subject(authentication.getName())
				.claim("scope", scope)
				.build();
		// @formatter:on
    JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
        JwsHeader.with(MacAlgorithm.HS512).build(),
        jwtClaimsSet);
    String jwt = this.encoder.encode(jwtEncoderParameters).getTokenValue();
    return Map.of("access-token", jwt);
  }

  @GetMapping("/hello")
  public String hello(Authentication authentication) {
    return "Hello, " + authentication.getName() + "!";
  }

  @GetMapping("/admin")
  public String admin(Authentication authentication) {
    return "Hello, " + authentication.getName() + "!";
  }

}