package com.furkankozmac.schoolsystem.controller;

import com.furkankozmac.schoolsystem.dto.CourseResponse;
import com.furkankozmac.schoolsystem.dto.GradeResponse;
import com.furkankozmac.schoolsystem.entity.Enrollment;
import com.furkankozmac.schoolsystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/courses")
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        return ResponseEntity.ok(studentService.getAllCourses());
    }

    @PostMapping("/courses/{courseId}/enroll")
    public ResponseEntity<String> enroll(@PathVariable Long courseId) {
        studentService.enroll(courseId);
        return ResponseEntity.ok("Successfully enrolled");
    }

    @GetMapping("/my-grades")
    public ResponseEntity<List<GradeResponse>> getMyGrades() {
        return ResponseEntity.ok(studentService.getMyGrades());
    }
}
