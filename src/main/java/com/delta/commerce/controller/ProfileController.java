package com.delta.commerce.controller;

import com.delta.commerce.dto.request.ProfileRequestDto;
import com.delta.commerce.entity.Profile;
import com.delta.commerce.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping
    public ResponseEntity<Profile> createProfile(@RequestBody @Valid ProfileRequestDto dto) {
        return ResponseEntity.ok(this.profileService.createProfile(dto));
    }

    @GetMapping
    public ResponseEntity<Page<Profile>> getAll(
            @RequestParam(name = "name", required = false) String name,
            Pageable pageable
    ) {
        return ResponseEntity.ok(this.profileService.getAllProfiles(name, pageable));
    }


}
