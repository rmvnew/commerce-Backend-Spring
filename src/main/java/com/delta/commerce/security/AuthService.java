package com.delta.commerce.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Value("${JWT_SECRET}")
    private String secret;

    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWT.require(algorithm).build().verify(token);
            return true;
        } catch (JWTVerificationException exception){
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException exception){
            // A exceção será lançada se o token for inválido. Você pode lidar com isso como preferir.
            return null;
        }
    }

}
