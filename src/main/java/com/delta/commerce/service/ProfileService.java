package com.delta.commerce.service;

import com.delta.commerce.dto.request.ProfileRequestDto;
import com.delta.commerce.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfileService {

    Profile createProfile(ProfileRequestDto dto);

    Page<Profile> getAllProfiles(String name, Pageable page);

    Profile findById(Long id);

    Profile updateProfile(ProfileRequestDto dto, Long id);

    void changeStatus(Long id);

}
