package com.idealstudy.mvp.application.dto.classroom.inclass.exam;

import com.idealstudy.mvp.enums.classroom.AssessmentType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ExamDto {

    private Long id;

    private String classroomId;

    private String classroomName;

    private AssessmentType assessmentType;

    private String title;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String examText;

    private String examUri;

    private String feedbackToAll;
}
