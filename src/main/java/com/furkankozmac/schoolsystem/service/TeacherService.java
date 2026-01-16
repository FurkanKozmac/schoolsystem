package com.furkankozmac.schoolsystem.service;

import com.furkankozmac.schoolsystem.entity.Enrollment;
import com.furkankozmac.schoolsystem.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final EnrollmentRepository enrollmentRepository;

    public void assignGrade(Long enrollmentId, Double grade) {

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found!"));

        // In a real app, you'd check if the logged-in Teacher actually owns this course.
        // For now, we trust anyone with ROLE_TEACHER.

        enrollment.setGrade(grade);

        enrollmentRepository.save(enrollment);
    }
}