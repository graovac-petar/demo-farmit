package com.farmit.demo_farmit.service;

import com.farmit.demo_farmit.DTO.UserRequest;
import com.farmit.demo_farmit.DTO.UserResponse;
import com.farmit.demo_farmit.entity.User;
import com.farmit.demo_farmit.exception.BadRequestException;
import com.farmit.demo_farmit.exception.ResourceNotFoundException;
import com.farmit.demo_farmit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        //check if user already exists
        if(userRepository.existsByEmail(userRequest.getEmail())) {
            throw new BadRequestException("User already exists");
        }

        User user = mapToUser(userRequest);
        userRepository.save(user);
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getNumberBug(), user.getNumberFeature());
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getNumberBug(), user.getNumberFeature());
    }

    public User mapToUser(UserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setNumberBug(0);
        user.setNumberFeature(0);
        return user;
    }
}
