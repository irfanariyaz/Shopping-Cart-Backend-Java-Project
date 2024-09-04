package com.irfanacode.dreamshops.controller;


import com.irfanacode.dreamshops.dto.UserDto;
import com.irfanacode.dreamshops.exceptions.AlreadyExistException;
import com.irfanacode.dreamshops.exceptions.ResourceNotFoundException;
import com.irfanacode.dreamshops.model.User;
import com.irfanacode.dreamshops.request.CreateUserRequest;
import com.irfanacode.dreamshops.request.UpdateUserRequest;
import com.irfanacode.dreamshops.response.ApiResponse;
import com.irfanacode.dreamshops.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            UserDto  userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("User retrieved successfully", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/Add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
        try {
            User  user = userService.createUser(request);
            UserDto  userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("User created successfully", userDto));
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @PutMapping("/{userId}/update")
    public  ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request, @PathVariable Long userId) {
        try {
            User user = userService.updateUser(request, userId);
            UserDto  userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("User updated successfully", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("User deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
