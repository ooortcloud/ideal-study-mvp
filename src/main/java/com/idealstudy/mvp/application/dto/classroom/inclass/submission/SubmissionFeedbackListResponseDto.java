package com.idealstudy.mvp.application.dto.classroom.inclass.submission;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class SubmissionFeedbackListResponseDto {

    private String studentId;

    private Long assessmentId;

    private String classroomName;

    private String assessmentTitle;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;
}
