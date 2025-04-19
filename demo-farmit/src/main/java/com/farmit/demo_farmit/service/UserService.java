package com.farmit.demo_farmit.service;

import com.farmit.demo_farmit.DTO.UserRequest;
import com.farmit.demo_farmit.DTO.UserResponse;
import com.farmit.demo_farmit.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    UserResponse getUserById(Long id);
}
