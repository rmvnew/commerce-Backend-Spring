package com.delta.commerce.controller;


import com.delta.commerce.dto.request.CreateUserRequestDto;
import com.delta.commerce.dto.request.RecoverPassRequestDto;
import com.delta.commerce.dto.request.UpdateUserRequestDto;
import com.delta.commerce.dto.response.UserResponse;
import com.delta.commerce.mappers.UserMapper;
import com.delta.commerce.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "users")
@Tag(name = "User", description = "Controller para serviços de Usuários")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN_WRITE')")
    public void createUser(@RequestBody @Valid CreateUserRequestDto dto) {

        this.userService.createUser(dto);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN_READ')")
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @RequestParam(required = false, name = "name") String name,
            Pageable page
    ) {
        var currentName = name == null ? "" : name;
        return ResponseEntity.ok(this.userService.getAllUsers(currentName, page));
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('USER_WRITE','ADMIN_WRITE')")
    public ResponseEntity<UserResponse> updateUser(
            @RequestBody @Valid UpdateUserRequestDto dto,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(this.userService.userUpdate(dto, id));
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN_READ')")
    public ResponseEntity<UserResponse> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(this.userMapper.toDto(this.userService.findById(id)));
    }

    @GetMapping(value = "/email")
    @PreAuthorize("hasAnyAuthority('ADMIN_READ','USER_READ')")
    public ResponseEntity<UserResponse> findUserByEmail(
            @RequestParam(required = true, value = "email") String email
    ) {
        return ResponseEntity.ok(this.userMapper.toDto(this.userService.findUserByEmail(email)));
    }

    @PostMapping(value = "/recover-code")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void recoverCode(
            @RequestParam(required = true, value = "email") String email
    ) {
        this.userService.recoverCode(email);
    }

    @PostMapping(value = "/recover-pass")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void recoverPassword(
            @RequestBody @Valid RecoverPassRequestDto passDto
    ) {
        this.userService.recoverPass(passDto);
    }

    @PutMapping(value = "/change-status/{user_id}")
    @PreAuthorize("hasAuthority('ADMIN_WRITE')")
    public ResponseEntity<UserResponse> changeStatus(
            @PathVariable Long user_id
    ) {
        return ResponseEntity.ok(this.userService.changeStatus(user_id));
    }

}
