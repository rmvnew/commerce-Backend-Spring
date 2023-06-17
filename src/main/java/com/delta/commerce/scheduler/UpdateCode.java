package com.delta.commerce.scheduler;


import com.delta.commerce.exception.CustomException;
import com.delta.commerce.exception.ErrorCustom;
import com.delta.commerce.repository.UserRepository;

import java.util.TimerTask;

public class UpdateCode extends TimerTask {
    private String email;

    private UserRepository userRepository;

    public UpdateCode(
            String email,
            UserRepository userRepository) {
        this.email = email;

        this.userRepository = userRepository;

    }

    @Override
    public void run() {
        var user = this.userRepository.findUserByUserEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCustom.NOT_FOUND));

        user.setRecoverCode("");

        userRepository.save(user);
    }
}
