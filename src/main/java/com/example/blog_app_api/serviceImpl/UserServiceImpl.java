package com.example.blog_app_api.serviceImpl;

import com.example.blog_app_api.entity.Role;
import com.example.blog_app_api.entity.Users;
import com.example.blog_app_api.exceptions.EmailAlreadyExistsException;
import com.example.blog_app_api.exceptions.ResourceNotFoundException;
import com.example.blog_app_api.repositories.RoleRepository;
import com.example.blog_app_api.repositories.UserRepository;
import com.example.blog_app_api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.blog_app_api.config.AppConstants.ROLE_USER;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Users registerUser(Users user) throws EmailAlreadyExistsException {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findById(ROLE_USER).get();
        user.setRoles(List.of(role));
        try {
            Users savedUser = userRepository.save(user);
        } catch (Exception e) {
            throw new EmailAlreadyExistsException("Email already taken");
        }

        return user;
    }

    @Override
    public Users createUser(Users user) {
        return userRepository.save(user);
    }

    @Override
    public Users updateUser(Users user, Integer userId) {
        Users curUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " id ", userId));
        curUser.setName(user.getName());
        curUser.setEmail(user.getEmail());
        curUser.setPassword(user.getPassword());
        curUser.setAbout(user.getAbout());

        return userRepository.save(user);
    }

    @Override
    public Users getUserByUserId(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " id ", userId));
    }

    @Override
    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "email:" + email, 0));
    }

    @Override
    public void deleteUser(Integer userId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " id ", userId));

        userRepository.delete(user);

    }

    @Override
    public List<Users> getAll() {
        return userRepository.findAll();
    }
}
