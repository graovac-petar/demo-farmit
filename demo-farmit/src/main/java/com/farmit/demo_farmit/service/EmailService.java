package com.farmit.demo_farmit.service;

import com.farmit.demo_farmit.DTO.EmailDetails;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendEmail(EmailDetails emailDetails);
}
