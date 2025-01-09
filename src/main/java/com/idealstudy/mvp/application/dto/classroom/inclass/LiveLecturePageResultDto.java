package com.idealstudy.mvp.application.dto.classroom.inclass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class LiveLecturePageResultDto {

    private List<LiveLectureDto> dtoList;

    private int totalPage;

    private int page;

    private int size;

    private int startPage, endPage;

    private boolean hasPrev, hasNext;

    private List<Integer> pageList;
}
