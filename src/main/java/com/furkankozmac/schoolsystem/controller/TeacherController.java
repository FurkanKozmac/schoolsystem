package com.furkankozmac.schoolsystem.controller;

import com.furkankozmac.schoolsystem.dto.CourseRequest;
import com.furkankozmac.schoolsystem.dto.CourseResponse;
import com.furkankozmac.schoolsystem.dto.StudentGradeResponse;
import com.furkankozmac.schoolsystem.service.CourseService;
import com.furkankozmac.schoolsystem.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TeacherController {

    private final CourseService courseService;
    private final TeacherService teacherService;

    @PostMapping("/courses")
    public ResponseEntity<CourseResponse> createCourse(@RequestBody CourseRequest request) {
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

    @GetMapping("/courses")
    public ResponseEntity<List<CourseResponse>> getMyCourses() {
        return ResponseEntity.ok(teacherService.getMyCourses());
    }

    // GET /api/teacher/courses/5/students
    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<List<StudentGradeResponse>> getStudents(@PathVariable Long courseId) {
        return ResponseEntity.ok(teacherService.getEnrolledStudents(courseId));
    }

}
