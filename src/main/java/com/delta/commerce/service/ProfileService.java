package com.delta.commerce.service;

import com.delta.commerce.dto.request.ProfileRequestDto;
import com.delta.commerce.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProfileService {

    Profile createProfile(ProfileRequestDto dto);

    Page<Profile> getAllProfiles(String name, Pageable page);

    Profile findById(Long id);

    Profile updateProfile(ProfileRequestDto dto, Long id);

    void deleteProfile(Long id);

    List<Profile> getListProfiles(String name);

}
