package com.furkankozmac.schoolsystem.controller;

import com.furkankozmac.schoolsystem.dto.CourseRequest;
import com.furkankozmac.schoolsystem.entity.Course;
import com.furkankozmac.schoolsystem.service.CourseService;
import com.furkankozmac.schoolsystem.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TeacherController {

    private final CourseService courseService;
    private final TeacherService teacherService;

    @PostMapping("/courses")
    public ResponseEntity<Course> createCourse(@RequestBody CourseRequest request) {

        var auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("User: " + auth.getName());
        System.out.println("Authorities: " + auth.getAuthorities());

        return ResponseEntity.ok(courseService.createCourse(request));
    }

    @PutMapping("/enrollments/{enrollmentId}/grade")
    public ResponseEntity<String> gradeStudent(
            @PathVariable Long enrollmentId,
            @RequestParam Double grade
    ) {
        teacherService.assignGrade(enrollmentId, grade);
        return ResponseEntity.ok("Grade assigned successfully");
    }

}
