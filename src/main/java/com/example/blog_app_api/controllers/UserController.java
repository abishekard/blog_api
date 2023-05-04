package com.example.blog_app_api.controllers;

import com.example.blog_app_api.entity.Users;
import com.example.blog_app_api.exceptions.ApiException;
import com.example.blog_app_api.exceptions.EmailAlreadyExistsException;
import com.example.blog_app_api.responseDto.ApiResponse;
import com.example.blog_app_api.requestDto.JwtAuthRequest;
import com.example.blog_app_api.responseDto.UserResponseDto;
import com.example.blog_app_api.requestDto.UserRequestDto;
import com.example.blog_app_api.security.JwtAuthResponse;
import com.example.blog_app_api.security.JwtTokenHelper;
import com.example.blog_app_api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDto userDto) {
        Users createdUser = userService.createUser(modelMapper.map(userDto, Users.class));

        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(createdUser, UserResponseDto.class));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserRequestDto userRequestDto, @PathVariable("userId") int userId) {
        Users updatedUser = this.userService.updateUser(modelMapper.map(userRequestDto, Users.class), userId);
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(updatedUser, UserResponseDto.class));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("User deleted", true));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/")
    public ResponseEntity<?> getAllUsers() {
        List<Users> users = userService.getAll();
        List<UserResponseDto> userList = users.stream().map((data) -> modelMapper.map(data, UserResponseDto.class)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getSingleUser(@PathVariable int userId) {
        Users user = userService.getUserByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(user, UserResponseDto.class));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDto userRequestDto) throws EmailAlreadyExistsException {
        userService.registerUser(modelMapper.map(userRequestDto, Users.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("User Registered successfully", true));
    }

    @PostMapping("/login")
    public ResponseEntity<?> createToken(@RequestBody JwtAuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            Users user = userService.getUserByEmail(request.getEmail());
            String token = jwtTokenHelper.generateAccessToken(user.getEmail());
            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
            jwtAuthResponse.setToken("Bearer " + token);
            jwtAuthResponse.setUser(modelMapper.map(user, UserResponseDto.class));
            return ResponseEntity.status(HttpStatus.OK).body(jwtAuthResponse);
        } catch (BadCredentialsException e) {
            throw new ApiException("Invalid username or password");
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.toString());
        }

    }

}
