package com.idealstudy.mvp.application.dto.classroom.inclass;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AttendanceDto {

    private Long id;

    private String classroomId;

    private String createdBy;

    private LocalDateTime regDate;  // 출석 일시

    private LocalDateTime checkOutDate;  // 퇴실 일시
}
