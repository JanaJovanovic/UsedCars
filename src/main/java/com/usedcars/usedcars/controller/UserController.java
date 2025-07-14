package com.usedcars.usedcars.controller;


import com.usedcars.usedcars.auth.AuthenticationService;
import com.usedcars.usedcars.auth.RegisterRequestByAdmin;
import com.usedcars.usedcars.dtos.UserDto;
import com.usedcars.usedcars.model.User;
import com.usedcars.usedcars.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

//    @GetMapping("/{id}")
//    public String getUser(@PathVariable Long id,
//                           Model model){
//        UserDto userDto = userService.getUserById(id);
//        model.addAttribute("user",userDto);
//        return "user-details";
//    }
//
//    @GetMapping
//    public String getUsers(@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
//                                  @RequestParam(name = "pageSize", defaultValue = "50")  Integer pageSize,
//                                  Model model){
//        Page<UserDto> userPage = userService.getAllUsers(PageRequest.of(pageNumber, pageSize));
//        model.addAttribute("userPage",userPage);
//        return "users";
//    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
    }

    @PostMapping
    public User addUser(@RequestBody RegisterRequestByAdmin request) throws IOException {
        return authenticationService.registerByAdmin(request);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id,@RequestBody RegisterRequestByAdmin request) throws IOException {
        return userService.updateUser(id,request);
    }

    @GetMapping("/role")
    public String getRole(HttpServletRequest httpServletRequest) throws IOException {
        User user = authenticationService.getUserOrNot(httpServletRequest);
        if (user != null){
            return user.getRole().toString();
        }else{
            return null;
        }
    }
}
