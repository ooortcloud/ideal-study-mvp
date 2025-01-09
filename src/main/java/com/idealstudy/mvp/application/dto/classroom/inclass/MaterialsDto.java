package com.idealstudy.mvp.application.dto.classroom.inclass;

import com.idealstudy.mvp.enums.classroom.MaterialsStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MaterialsDto {

    private Long id;

    private String classroomId;

    private String studentId;

    private Integer orderNum;

    private MaterialsStatus status;

    private String title;

    private String description;

    private String materialUri;
}
