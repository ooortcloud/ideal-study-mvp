package com.idealstudy.mvp.application.dto.classroom.inclass.submission;

import com.idealstudy.mvp.enums.classroom.AssessmentType;
import com.idealstudy.mvp.enums.classroom.SubmissionStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class SubmissionDto {

    private Long id;

    private String studentId;

    private String studentName;

    private String assessmentId;

    private String assessmentTitle;

    private AssessmentType type;

    private String submissionText;

    private String submissionUri;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private SubmissionStatus status;

    private String feedback;

    private Integer score;
}
