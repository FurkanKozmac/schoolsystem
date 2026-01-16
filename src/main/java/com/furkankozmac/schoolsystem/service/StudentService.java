package com.furkankozmac.schoolsystem.service;

import com.furkankozmac.schoolsystem.dto.CourseResponse;
import com.furkankozmac.schoolsystem.dto.GradeResponse;
import com.furkankozmac.schoolsystem.entity.Course;
import com.furkankozmac.schoolsystem.entity.Enrollment;
import com.furkankozmac.schoolsystem.entity.User;
import com.furkankozmac.schoolsystem.repository.CourseRepository;
import com.furkankozmac.schoolsystem.repository.EnrollmentRepository;
import com.furkankozmac.schoolsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;

    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public void enroll(Long courseId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User student = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Student not found"));

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        if (enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), courseId)) {
            throw new RuntimeException("Course already enrolled");
        }

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .enrolledAt(LocalDateTime.now())
                .attendance(0)
                .grade(null)
                .build();

        enrollmentRepository.save(enrollment);
    }

    // Helper: Map Entity -> DTO
    private CourseResponse mapToDto(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .maxStudents(course.getMaxStudents())
                .teacherName(course.getTeacher().getFullName())
                .build();
    }

    public List<GradeResponse> getMyGrades() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Student not found"));


        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(student.getId());

        // 3. Convert to DTOs
        return enrollments.stream()
                .map(e -> GradeResponse.builder()
                        .courseTitle(e.getCourse().getTitle())
                        .teacherName(e.getCourse().getTeacher().getFullName())
                        .grade(e.getGrade())
                        .attendance(e.getAttendance())
                        .build())
                .collect(Collectors.toList());
    }
}
