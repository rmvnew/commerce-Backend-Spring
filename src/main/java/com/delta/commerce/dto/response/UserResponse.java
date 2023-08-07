package com.delta.commerce.dto.response;

import com.delta.commerce.entity.Profile;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long userId;
    private String userCompleteName;
    private String userEnrollment;
    private String userEmail;
    private boolean isActive;
    private LocalDateTime createAt;
    Profile profile;
}

