package com.furkankozmac.schoolsystem.service;

import com.furkankozmac.schoolsystem.dto.CourseRequest;
import com.furkankozmac.schoolsystem.entity.Course;
import com.furkankozmac.schoolsystem.entity.User;
import com.furkankozmac.schoolsystem.repository.CourseRepository;
import com.furkankozmac.schoolsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public Course createCourse(CourseRequest request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User teacher = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Teacher not found"));

        // 3. Create the Course
        Course course = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .maxStudents(request.getMaxStudents())
                .teacher(teacher) // Automatically link to the logged-in user
                .build();

        return courseRepository.save(course);
    }
}
