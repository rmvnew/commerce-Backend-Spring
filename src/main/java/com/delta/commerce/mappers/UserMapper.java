package com.delta.commerce.mappers;

import com.delta.commerce.dto.response.UserResponse;
import com.delta.commerce.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toDto(User user);

}
