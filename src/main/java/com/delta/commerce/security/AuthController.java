package com.delta.commerce.security;


import com.delta.commerce.dto.LoginDto;
import com.delta.commerce.entity.User;
import com.delta.commerce.model.TokenModel;
import com.delta.commerce.service.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    @Tag(name = "Login", description = "Controller para serviços de login")
    public ResponseEntity<TokenModel> login(@RequestBody LoginDto login){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(login.login(),
                        login.password());

        Authentication authenticate = this.authenticationManager
                .authenticate(usernamePasswordAuthenticationToken);

        var user = (User)authenticate.getPrincipal();

        return ResponseEntity.ok(tokenService.generateToken(user));

    }

}
