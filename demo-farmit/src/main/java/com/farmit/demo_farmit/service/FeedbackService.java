package com.farmit.demo_farmit.service;

import com.farmit.demo_farmit.DTO.FeedbackRequest;
import com.farmit.demo_farmit.DTO.FeedbackResponse;
import com.farmit.demo_farmit.DTO.FeedbackUpdate;
import com.farmit.demo_farmit.entity.Feedback;
import com.farmit.demo_farmit.entity.enums.Status;
import com.farmit.demo_farmit.entity.enums.Type;
import com.farmit.demo_farmit.entity.enums.Urgency;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FeedbackService {
    List<FeedbackResponse> getAllFeedbacks();
    FeedbackResponse createFeedback(FeedbackRequest feedbackRequest);
    FeedbackResponse getFeedbackById(Long id);
    FeedbackResponse updateFeedback(Long id, FeedbackUpdate feedbackUpdate);
    void deleteFeedback(Long id);
    List<FeedbackResponse> filterFeedbacks(Type type, Status status);
}
