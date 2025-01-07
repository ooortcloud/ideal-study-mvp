package com.idealstudy.mvp.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class LikedClassroomDto {

    private Long likedId;
    private String userId;
    private List<String> createdBy;
}
