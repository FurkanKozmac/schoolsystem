package com.furkankozmac.schoolsystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentGradeResponse {
    private Long enrollmentId;
    private String studentName;
    private String studentEmail;
    private Double grade;
}
