package com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass;

import com.idealstudy.mvp.enums.classroom.EnrollmentStatus;
import com.idealstudy.mvp.infrastructure.jpa.entity.BaseEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.StudentEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "enrollment")
@Getter
@Setter
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long enrollmentId;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private ClassroomEntity classroom;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    private String curScore;  // 현재 점수

    private String targetScore;  // 목표 점수

    private String request;  // 고민되는 점, 선생님께 바라는 점

    private String determination;  // 각오 및 마지막 한마디
}
