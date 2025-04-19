package com.farmit.demo_farmit.DTO;

import com.farmit.demo_farmit.entity.User;
import com.farmit.demo_farmit.entity.enums.Status;
import com.farmit.demo_farmit.entity.enums.Type;
import com.farmit.demo_farmit.entity.enums.Urgency;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponse {

    private Type type;
    private String description;
    private Status status;
    private Urgency urgency;
    private String response;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
}
