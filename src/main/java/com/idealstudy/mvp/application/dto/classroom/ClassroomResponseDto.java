package com.idealstudy.mvp.application.dto.classroom;

import com.idealstudy.mvp.enums.classroom.ClassroomStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class ClassroomResponseDto {
    private String id;
    private String title;
    private String description;
    private int capacity;
    private String createdBy;
    private String thumbnail;
    private ClassroomStatus status;
}
