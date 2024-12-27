package com.idealstudy.mvp.application.dto.classroom.inclass.exam;

import com.idealstudy.mvp.enums.classroom.AssessmentStatus;
import com.idealstudy.mvp.enums.classroom.AssessmentType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ExamListResponseDto {

    private Long id;

    private String classroomId;

    private AssessmentType assessmentType;

    private String title;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private AssessmentStatus status;
}
