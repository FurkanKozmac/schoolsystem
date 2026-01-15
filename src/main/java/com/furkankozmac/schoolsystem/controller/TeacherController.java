package com.furkankozmac.schoolsystem.controller;

import com.furkankozmac.schoolsystem.dto.CourseRequest;
import com.furkankozmac.schoolsystem.entity.Course;
import com.furkankozmac.schoolsystem.service.CourseService;
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

    @PostMapping("/courses")
    public ResponseEntity<Course> createCourse(@RequestBody CourseRequest request) {

        // 👇 ADD THIS DEBUG BLOCK
        var auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("User: " + auth.getName());
        System.out.println("Authorities: " + auth.getAuthorities());
        // 👆 END DEBUG BLOCK

        return ResponseEntity.ok(courseService.createCourse(request));
    }
}
