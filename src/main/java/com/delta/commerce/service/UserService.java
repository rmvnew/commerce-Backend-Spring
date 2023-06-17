package com.delta.commerce.service;


import com.delta.commerce.dto.request.CreateUserRequestDto;
import com.delta.commerce.dto.request.RecoverPassRequestDto;
import com.delta.commerce.dto.request.UpdateUserRequestDto;
import com.delta.commerce.dto.response.UserResponse;
import com.delta.commerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    void createUser(CreateUserRequestDto dto);

    Page<UserResponse> getAllUsers(String name, Pageable pageable);

    UserResponse userUpdate(UpdateUserRequestDto dto, Long id);

    UserResponse findById(Long id);

    void recoverCode(String email);

    void recoverPass(RecoverPassRequestDto dto);

    UserResponse changeStatus(Long user_id);

    User getLoggedInUser();

    boolean isUserLoggedIn(User user);
}
