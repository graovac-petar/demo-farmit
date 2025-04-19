package com.farmit.demo_farmit.controller;

import com.farmit.demo_farmit.DTO.FeedbackRequest;
import com.farmit.demo_farmit.DTO.FeedbackResponse;
import com.farmit.demo_farmit.DTO.UserRequest;
import com.farmit.demo_farmit.DTO.UserResponse;
import com.farmit.demo_farmit.entity.Feedback;
import com.farmit.demo_farmit.entity.User;
import com.farmit.demo_farmit.service.FeedbackService;
import com.farmit.demo_farmit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createFeedback(@RequestBody UserRequest UserRequest) {
        UserResponse userResponse = userService.createUser(UserRequest);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getFeedbackById(@PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }


}
