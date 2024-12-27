package com.idealstudy.mvp.application.dto.classroom.inclass.exam;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class ExamFeedbackDetailResponse extends ExamFeedbackListResponseDto{

    private String feedbackToAll;
}
