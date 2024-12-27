package com.idealstudy.mvp.application.dto.classroom.inclass.submission;

import com.idealstudy.mvp.enums.classroom.AssessmentType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SubmissionListResponseDto {

    private String studentId;

    private String studentName;

    private String title;

    private AssessmentType type;
}
