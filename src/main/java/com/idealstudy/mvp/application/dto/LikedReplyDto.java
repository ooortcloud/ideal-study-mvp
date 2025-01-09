package com.idealstudy.mvp.application.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LikedReplyDto {
    private Long likedId;
    private String userId;
    private List<String> createdBy;
}
