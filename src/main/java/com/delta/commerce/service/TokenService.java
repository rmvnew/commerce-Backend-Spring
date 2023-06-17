package com.delta.commerce.service;

import com.delta.commerce.entity.User;
import com.delta.commerce.model.TokenModel;

public interface TokenService {

    TokenModel generateToken(User user);

    String getSubject(String token);

}
