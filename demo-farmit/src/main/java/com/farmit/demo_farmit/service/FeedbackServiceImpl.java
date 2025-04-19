package com.farmit.demo_farmit.service;

import com.farmit.demo_farmit.DTO.EmailDetails;
import com.farmit.demo_farmit.DTO.FeedbackRequest;
import com.farmit.demo_farmit.DTO.FeedbackResponse;
import com.farmit.demo_farmit.DTO.FeedbackUpdate;
import com.farmit.demo_farmit.entity.Feedback;
import com.farmit.demo_farmit.entity.User;
import com.farmit.demo_farmit.entity.enums.Status;
import com.farmit.demo_farmit.entity.enums.Type;
import com.farmit.demo_farmit.entity.enums.Urgency;
import com.farmit.demo_farmit.exception.BadRequestException;
import com.farmit.demo_farmit.exception.ResourceNotFoundException;
import com.farmit.demo_farmit.repository.FeedbackRepository;
import com.farmit.demo_farmit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    @Override
    public List<FeedbackResponse> getAllFeedbacks() {
        List <Feedback> feedbacks = feedbackRepository.findAll();
        return feedbacks.stream().map(this::FeedbackToResponse).toList();
    }

    @Override
    public FeedbackResponse createFeedback(FeedbackRequest feedbackRequest) {
        User user = userRepository.findById(feedbackRequest.getUserId()).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User of feedback not found");
        }
        Feedback feedback = RequestToFeedback(feedbackRequest, user);

        if(feedbackRequest.getType().equals(Type.BUG)) {
            user.setNumberBug(user.getNumberBug() + 1);
        } else if (feedbackRequest.getType().equals(Type.FEATURE)) {
            user.setNumberFeature(user.getNumberFeature() + 1);
        }

        userRepository.save(user);

        feedbackRepository.save(feedback);
        return FeedbackToResponse(feedback);
    }

    @Override
    public FeedbackResponse getFeedbackById(Long id) {
        Feedback feedback = feedbackRepository.findById(id).orElse(null);
        if (feedback == null) {
            throw new ResourceNotFoundException("Feedback not found");
        }
        return FeedbackToResponse(feedback);
    }

    @Override
    public FeedbackResponse updateFeedback(Long id, FeedbackUpdate feedbackUpdate) {
        Feedback feedback = feedbackRepository.findById(id).orElse(null);
        if (feedback == null) {
            throw new ResourceNotFoundException("Feedback not found");
        }
        User user = userRepository.findById(feedback.getUser().getId()).orElse(null);
        if (user == null) {
            throw new BadRequestException("User not found");
        }


        if (feedbackUpdate.getStatus() == Status.NEW || feedbackUpdate.getStatus() == Status.IN_PROGRESS || feedbackUpdate.getStatus() == Status.RESOLVED) {
            feedback.setStatus(feedbackUpdate.getStatus());
        }
        else {

            throw new BadRequestException("Invalid status");
        }

        if (feedbackUpdate.getUrgency() == Urgency.LOW || feedbackUpdate.getUrgency() == Urgency.MEDIUM || feedbackUpdate.getUrgency() == Urgency.HIGH || feedbackUpdate.getUrgency() == Urgency.CRITICAL) {
            feedback.setUrgency(feedbackUpdate.getUrgency());
        }
        else {
            throw new BadRequestException("Invalid urgency");
        }
        feedback.setResponse(feedbackUpdate.getResponse());

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("Feedback Update")
                .messageBody("Your feedback has been updated. \n" +
                        "Status: " + feedback.getStatus() + "\n" +
                        "Urgency: " + feedback.getUrgency() + "\n" +
                        "Response: " + feedback.getResponse())
                .build();

        emailService.sendEmail(emailDetails);
        feedbackRepository.save(feedback);

        return FeedbackToResponse(feedback);
    }

    @Override
    public void deleteFeedback(Long id) {
        Feedback feedback = feedbackRepository.findById(id).orElse(null);
        if (feedback == null) {
            throw new ResourceNotFoundException("Feedback not found");
        }
        feedbackRepository.delete(feedback);
    }

    @Override
    public List<FeedbackResponse> filterFeedbacks(Type type, Status status) {
        if(type != Type.BUG  && type != Type.FEATURE && type != Type.OTHER && type!=null) {
            throw new BadRequestException("Type must be one of the following: BUG, FEATURE, OTHER");
        }
        if(status != Status.NEW && status != Status.IN_PROGRESS && status != Status.RESOLVED && status != null) {
            throw new BadRequestException("Status must be one of the following: NEW, IN_PROGRESS, RESOLVED");
        }
        List<Feedback> feedbacks = feedbackRepository.findAll();
        if (type != null) {
            feedbacks = feedbacks.stream()
                    .filter(feedback -> feedback.getType().equals(type))
                    .toList();
        }
        if (status != null) {
            feedbacks = feedbacks.stream()
                    .filter(feedback -> feedback.getStatus().equals(status))
                    .toList();
        }
        return feedbacks.stream().map(this::FeedbackToResponse).toList();
    }

    public Feedback RequestToFeedback(FeedbackRequest request, User user) {
        Feedback feedback = new Feedback();
        feedback.setType(request.getType());
        feedback.setDescription(request.getDescription());
        feedback.setStatus(Status.NEW);
        feedback.setUrgency(Urgency.LOW);
        feedback.setResponse("");
        feedback.setUser(user);
        return feedback;
    }

    public FeedbackResponse FeedbackToResponse(Feedback feedback) {
        FeedbackResponse response = new FeedbackResponse();
        response.setType(feedback.getType());
        response.setDescription(feedback.getDescription());
        response.setStatus(feedback.getStatus());
        response.setUrgency(feedback.getUrgency());
        response.setResponse(feedback.getResponse());
        response.setCreatedAt(feedback.getCreatedAt());
        response.setUpdatedAt(feedback.getUpdatedAt());
        response.setUserId(feedback.getUser().getId());
        return response;
    }


}
