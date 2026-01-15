package com.furkankozmac.schoolsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int maxStudents;

    // Relationship: One Teacher teaches Many Courses
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;
}