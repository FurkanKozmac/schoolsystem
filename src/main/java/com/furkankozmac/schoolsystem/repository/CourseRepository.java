package com.furkankozmac.schoolsystem.repository;
import com.furkankozmac.schoolsystem.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    // Find courses created by a specific teacher
    List<Course> findByTeacherId(Long teacherId);
}