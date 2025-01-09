package com.idealstudy.mvp.application.dto.classroom.inclass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class RecordLectureDto {

    private Long id;

    private String classroomId;

    private Integer orderNum;

    private String title;

    private String description;

    private int playtime;

    private String url;

    private LocalDateTime regDate;

    private LocalDateTime modDate;
}
