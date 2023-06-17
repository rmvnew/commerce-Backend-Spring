package com.delta.commerce.dto.response;

import com.delta.commerce.entity.Profile;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserResponse {
    private Long userId;
    private String userCompleteName;
    private String userEnrollment;
    private String userEmail;
    private boolean isActive;
    private LocalDateTime createAt;
    Set<Profile> profiles;
}

