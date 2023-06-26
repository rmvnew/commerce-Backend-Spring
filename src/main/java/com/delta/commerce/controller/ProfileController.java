package com.delta.commerce.controller;

import com.delta.commerce.dto.request.ProfileRequestDto;
import com.delta.commerce.entity.Profile;
import com.delta.commerce.service.ProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/profile")
@Tag(name = "Profile", description = "Controller para serviços de perfil")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN_WRITE')")
    public ResponseEntity<Profile> createProfile(@RequestBody @Valid ProfileRequestDto dto) {
        return ResponseEntity.ok(this.profileService.createProfile(dto));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN_READ')")
    public ResponseEntity<Page<Profile>> getAll(
            @RequestParam(name = "name", required = false) String name,
            Pageable pageable
    ) {
        return ResponseEntity.ok(this.profileService.getAllProfiles(name, pageable));
    }


}
