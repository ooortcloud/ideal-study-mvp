package com.idealstudy.mvp.application.dto.classroom.preclass;

import com.idealstudy.mvp.enums.classroom.EnrollmentStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EnrollmentDto {

    private long enrollmentId;

    private String classroomId;

    private String studentId;

    private String createdBy;

    private EnrollmentStatus status;

    private String curScore;  // 현재 점수

    private String targetScore;  // 목표 점수

    private String request;  // 고민되는 점, 선생님께 바라는 점

    private String determination;  // 각오 및 마지막 한마디
}
