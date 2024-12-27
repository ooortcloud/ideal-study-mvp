package com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass;

import com.idealstudy.mvp.enums.classroom.AssessmentType;
import com.idealstudy.mvp.enums.classroom.SubmissionStatus;
import com.idealstudy.mvp.infrastructure.jpa.entity.BaseEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.StudentEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "submission")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubmissionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    @ManyToOne
    @JoinColumn(name =  "assessment_id")
    private AssessmentEntity assessment;

    @Enumerated(value = EnumType.STRING)
    private AssessmentType type;

    private String submissionText;

    private String submissionUri;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    @Enumerated(value = EnumType.STRING)
    private SubmissionStatus status;

    private String personalFeedback;

    private Integer score;
}
