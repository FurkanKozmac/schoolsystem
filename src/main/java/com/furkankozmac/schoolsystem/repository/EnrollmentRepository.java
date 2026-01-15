package com.furkankozmac.schoolsystem.repository;

import com.furkankozmac.schoolsystem.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    // For Student: See their own grades
    List<Enrollment> findByStudentId(Long studentId);

    // For Teacher: See students in a specific course
    List<Enrollment> findByCourseId(Long courseId);

    // Check if student is already enrolled
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
}