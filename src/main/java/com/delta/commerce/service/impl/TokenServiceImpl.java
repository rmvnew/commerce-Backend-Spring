package com.delta.commerce.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.delta.commerce.entity.User;
import com.delta.commerce.model.TokenModel;
import com.delta.commerce.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${spring.jwt.secret}")
    private String secret;

    @Value("${spring.jwt.expiration}")
    private String expiration;


    @Override
    public TokenModel generateToken(User user) {
        Date exp = Date.from(LocalDateTime.now()
                .plusHours(5)
                .toInstant(ZoneOffset.of("-04:00")));

        var currentToken = JWT.create()
                .withIssuer("lab")
                .withSubject(user.getUsername())
                .withClaim("id", user.getUserId())
                .withExpiresAt(exp)
                .sign(Algorithm.HMAC256(secret));

        return new TokenModel(currentToken, exp.toString(), user.getUsername());
    }

    @Override
    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("lab")
                .build().verify(token)
                .getSubject();
    }
}
