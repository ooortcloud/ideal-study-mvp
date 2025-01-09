package com.idealstudy.mvp.application.dto.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MemberPageResultDto {

    private List<MemberListDto> dtoList;

    private int totalPage;

    private int page;

    private int size;

    private int startPage, endPage;

    private boolean hasPrev, hasNext;

    private List<Integer> pageList;
}
