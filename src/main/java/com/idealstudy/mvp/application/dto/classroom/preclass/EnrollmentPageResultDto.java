package com.idealstudy.mvp.application.dto.classroom.preclass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class EnrollmentPageResultDto {

    private List<EnrollmentDto> dtoList;

    private int totalPage;

    private int page;

    private int size;

    private int startPage, endPage;

    private boolean hasPrev, hasNext;

    private List<Integer> pageList;
}
