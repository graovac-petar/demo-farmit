package com.farmit.demo_farmit.repository;

import com.farmit.demo_farmit.entity.Feedback;
import com.farmit.demo_farmit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
}
