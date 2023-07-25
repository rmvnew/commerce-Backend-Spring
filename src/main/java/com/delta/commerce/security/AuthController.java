package com.delta.commerce.security;


import com.delta.commerce.dto.LoginDto;
import com.delta.commerce.entity.User;
import com.delta.commerce.exception.CustomException;
import com.delta.commerce.exception.ErrorCustom;
import com.delta.commerce.exception.IncorrectPasswordException;
import com.delta.commerce.exception.UserNotFoundException;
import com.delta.commerce.model.TokenModel;
import com.delta.commerce.repository.UserRepository;
import com.delta.commerce.service.TokenService;
import com.delta.commerce.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    @Tag(name = "Login", description = "Controller para serviços de login")
    public ResponseEntity<TokenModel> login(@RequestBody LoginDto login) {

        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(login.login(),
                            login.password());

            Authentication authenticate = this.authenticationManager
                    .authenticate(usernamePasswordAuthenticationToken);

            var user = (User) authenticate.getPrincipal();

            var currentUser = this.userService.findById(user.getUserId());

            var tokenData = tokenService.generateToken(user);

            currentUser.setUserToken(tokenData.getToken());
            this.userRepository.save(currentUser);

            return ResponseEntity.ok(tokenData);
        } catch (BadCredentialsException e) {
            throw new IncorrectPasswordException("Senha incorreta!");
        }catch (UserNotFoundException e){
            throw new UserNotFoundException("Usuaŕio não encontrado!");
        }


    }


    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestBody Map<String, String> tokenMap) {
        String token = tokenMap.get("token");

        var isToken = authService.validateToken(token);

        if (token != null && isToken) {
            try {
                String username = authService.getUsernameFromToken(token);

                User user = userRepository.findUserByUserEmail(username)
                        .orElseThrow(() -> new CustomException(ErrorCustom.NOT_FOUND));

                String storedToken = user.getUserToken();

                if (storedToken.equals(token)) {
                    return ResponseEntity.ok("Token is valid");
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token invalid");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token invalid");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token not provided");
        }
    }


    @PostMapping("/logout")
    public void logout(@RequestBody Map<String, String> tokenMap) {

        String token = tokenMap.get("token");

        var isToken = authService.validateToken(token);

        if (token != null && isToken) {
            try {
                String username = authService.getUsernameFromToken(token);

                User user = userRepository.findUserByUserEmail(username)
                        .orElseThrow(() -> new CustomException(ErrorCustom.NOT_FOUND));

                String storedToken = user.getUserToken();

                if (storedToken.equals(token)) {

                    user.setUserToken(null);
                    this.userRepository.save(user);


                } else {
                    throw new CustomException(ErrorCustom.TOKEN_INVALID);
                }
            } catch (Exception e) {
                logger.error("Token invalid: " + e.getMessage());
            }
        } else
            throw new CustomException(ErrorCustom.TOKEN_NOT_PROVIDED);
    }
}



