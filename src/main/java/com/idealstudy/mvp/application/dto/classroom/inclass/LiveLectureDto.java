package com.idealstudy.mvp.application.dto.classroom.inclass;


import com.idealstudy.mvp.enums.classroom.Platform;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class LiveLectureDto {

    private Long id;

    private String classroomId;

    private String title;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String studySpace;

    private Platform platform;
}
