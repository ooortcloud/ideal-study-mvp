package com.idealstudy.mvp.application.dto.classroom.inclass.exam;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExamFeedbackListResponseDto {

    private Long id;

    private String classroomId;

    private String title;
}
