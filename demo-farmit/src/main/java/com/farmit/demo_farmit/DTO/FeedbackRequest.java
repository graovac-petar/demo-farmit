package com.farmit.demo_farmit.DTO;

import com.farmit.demo_farmit.entity.enums.Status;
import com.farmit.demo_farmit.entity.enums.Type;
import com.farmit.demo_farmit.entity.enums.Urgency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequest {
    private Type type;
    private String description;
    private Long userId;

}
