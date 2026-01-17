package com.furkankozmac.schoolsystem.service;

import com.furkankozmac.schoolsystem.dto.CourseRequest;
import com.furkankozmac.schoolsystem.dto.CourseResponse;
import com.furkankozmac.schoolsystem.dto.StudentGradeResponse;
import com.furkankozmac.schoolsystem.entity.Course;
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
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;

    public CourseResponse createCourse(CourseRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User teacher = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Teacher not found"));

        Course course = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .maxStudents(request.getMaxStudents())
                .teacher(teacher)
                .build();

        Course savedCourse = courseRepository.save(course);

        // Map to DTO before returning
        return CourseResponse.builder()
                .id(savedCourse.getId())
                .title(savedCourse.getTitle())
                .description(savedCourse.getDescription())
                .maxStudents(savedCourse.getMaxStudents())
                .teacherName(savedCourse.getTeacher().getFullName()) // Only send the Name
                .build();
    }

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
