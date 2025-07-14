package com.usedcars.usedcars.service.impl;


import com.usedcars.usedcars.auth.RegisterRequestByAdmin;
import com.usedcars.usedcars.dtos.UserDto;
import com.usedcars.usedcars.enums.Role;
import com.usedcars.usedcars.mappers.UserDtoMapper;
import com.usedcars.usedcars.model.User;
import com.usedcars.usedcars.repository.UserRepository;
import com.usedcars.usedcars.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDto getUserById(Long id) {
        User user =  userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User with ID:" + id + " does not exist;"));
        return userDtoMapper.userToUserDto(user);
    }

    @Override
    public Page<UserDto> getAllUsers(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        return new PageImpl<>(userDtoMapper.userToUserDto(page.getContent()),pageable, page.getTotalElements());
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        User user =  userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User with ID:" + id + " does not exist;"));
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public User updateUser(Long id, RegisterRequestByAdmin request) {
        User user =  userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User with ID:" + id + " does not exist;"));

        String firstname = request.getFirstname();
        if (firstname != null){
            user.setFirstname(firstname);
        }
        String lastname = request.getLastname();
        if (lastname != null){
            user.setLastname(lastname);
        }
        String email = request.getEmail();
        if (email != null){
            user.setEmail(email);
        }
        String role = request.getRole();
        if (lastname != null){
            user.setRole(role.toUpperCase().equals(Role.ADMIN.name()) ? Role.ADMIN : Role.USER);
        }
        String password = request.getPassword();
        if (password != null){
            user.setPassword(passwordEncoder.encode(password));
        }
        return userRepository.save(user);
    }
}
