package com.example.blog_app_api.controllers;

import com.example.blog_app_api.security.JwtTokenHelper;
import com.example.blog_app_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

   /* @PostMapping("/login")
    public ResponseEntity<?> createToken(@RequestBody JwtAuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            UserDto userDto = userService.getUserByEmail(request.getEmail());
            String token = jwtTokenHelper.generateAccessToken(userDto.getEmail());
            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
            jwtAuthResponse.setToken("Bearer "+token);
            jwtAuthResponse.setUser(userDto);
            return ResponseEntity.status(HttpStatus.OK).body(jwtAuthResponse);
        } catch (BadCredentialsException e) {
            throw new ApiException("Invalid username or password");
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.toString());
        }

    }*/


   /* @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserDto createUserDto = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUserDto);
    }*/
}
