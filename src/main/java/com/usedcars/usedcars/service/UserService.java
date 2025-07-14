package com.usedcars.usedcars.service;

import com.usedcars.usedcars.auth.RegisterRequestByAdmin;
import com.usedcars.usedcars.dtos.UserDto;
import com.usedcars.usedcars.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDto getUserById(Long id);

    Page<UserDto> getAllUsers(Pageable pageable);

    void deleteUserById(Long id);

    User updateUser(Long id, RegisterRequestByAdmin request);
}
