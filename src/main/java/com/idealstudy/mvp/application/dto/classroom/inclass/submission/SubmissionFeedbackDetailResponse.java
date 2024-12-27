package com.idealstudy.mvp.application.dto.classroom.inclass.submission;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class SubmissionFeedbackDetailResponse {

    private Long submissionId;

    private Long assessmentId;

    private String assessmentTitle;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private String personalFeedback;
}
