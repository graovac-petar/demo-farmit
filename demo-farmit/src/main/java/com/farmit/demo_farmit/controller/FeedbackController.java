package com.farmit.demo_farmit.controller;

import com.farmit.demo_farmit.DTO.FeedbackRequest;
import com.farmit.demo_farmit.DTO.FeedbackResponse;
import com.farmit.demo_farmit.DTO.FeedbackUpdate;
import com.farmit.demo_farmit.entity.Feedback;
import com.farmit.demo_farmit.entity.enums.Status;
import com.farmit.demo_farmit.entity.enums.Type;
import com.farmit.demo_farmit.entity.enums.Urgency;
import com.farmit.demo_farmit.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<List<FeedbackResponse>> getAllFeedbacks() {
        return ResponseEntity.ok(feedbackService.getAllFeedbacks());
    }

    @PostMapping
    public ResponseEntity<FeedbackResponse> createFeedback(@RequestBody FeedbackRequest feedbackRequest) {
        return ResponseEntity.ok(feedbackService.createFeedback(feedbackRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackResponse> getFeedbackById(@PathVariable Long id) {

        return ResponseEntity.ok(feedbackService.getFeedbackById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackResponse> updateFeedback(@PathVariable Long id, @RequestBody FeedbackUpdate feedback) {
        return ResponseEntity.ok(feedbackService.updateFeedback(id, feedback));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<FeedbackResponse>> filterFeedbacks(
            @RequestParam(required = false) Type type,
            @RequestParam(required = false) Status status) {
        return ResponseEntity.ok(feedbackService.filterFeedbacks(type, status));
    }

}
