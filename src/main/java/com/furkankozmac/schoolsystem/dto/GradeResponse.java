package com.furkankozmac.schoolsystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GradeResponse {
    private String courseTitle;
    private String teacherName;
    private Double grade;
    private Integer attendance;
}
