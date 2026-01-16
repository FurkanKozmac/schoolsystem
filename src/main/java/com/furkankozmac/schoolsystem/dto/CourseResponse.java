package com.furkankozmac.schoolsystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private String teacherName;
    private int maxStudents;
}
