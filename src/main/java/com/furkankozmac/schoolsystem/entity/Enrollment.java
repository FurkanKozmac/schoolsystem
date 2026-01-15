package com.furkankozmac.schoolsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Who is taking the class?
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    // Which class?
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // Academic Data
    private Double grade;        // e.g., 85.5 (Null means not graded yet)

    private Integer attendance;  // e.g., 90 (Percentage)

    private LocalDateTime enrolledAt;
}
