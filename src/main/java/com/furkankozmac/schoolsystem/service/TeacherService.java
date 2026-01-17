package com.furkankozmac.schoolsystem.service;

import com.furkankozmac.schoolsystem.dto.CourseResponse;
import com.furkankozmac.schoolsystem.dto.StudentGradeResponse;
import com.furkankozmac.schoolsystem.entity.Enrollment;
import com.furkankozmac.schoolsystem.entity.User;
import com.furkankozmac.schoolsystem.repository.CourseRepository;
import com.furkankozmac.schoolsystem.repository.EnrollmentRepository;
import com.furkankozmac.schoolsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public void assignGrade(Long enrollmentId, Double grade) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found!"));

        enrollment.setGrade(grade);
        enrollmentRepository.save(enrollment);
    }

    // NEW: Get courses created by the logged-in Teacher
    public List<CourseResponse> getMyCourses() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User teacher = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Teacher not found"));

        return courseRepository.findByTeacherId(teacher.getId()).stream()
                .map(c -> CourseResponse.builder()
                        .id(c.getId())
                        .title(c.getTitle())
                        .description(c.getDescription())
                        .maxStudents(c.getMaxStudents())
                        .teacherName(teacher.getFullName())
                        .build())
                .collect(Collectors.toList());
    }

    // NEW: Get all students enrolled in a specific course
    public List<StudentGradeResponse> getEnrolledStudents(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId).stream()
                .map(e -> StudentGradeResponse.builder()
                        .enrollmentId(e.getId())
                        .studentName(e.getStudent().getFullName())
                        .studentEmail(e.getStudent().getEmail())
                        .grade(e.getGrade())
                        .build())
                .collect(Collectors.toList());
    }
}