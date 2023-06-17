package com.delta.commerce.service;

import com.delta.commerce.dto.request.ProfileRequestDto;
import com.delta.commerce.entity.Profile;

public interface ProfileService {

    Profile createProfile(ProfileRequestDto dto);

}
