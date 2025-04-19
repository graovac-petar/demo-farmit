package com.farmit.demo_farmit.repository;

import com.farmit.demo_farmit.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
}
