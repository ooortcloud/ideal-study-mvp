package com.idealstudy.mvp.application.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.idealstudy.mvp.enums.classroom.Visibility;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ReplyDto {

    private Long commentId;

    private String content;

    private Visibility visibility;

    private String createdBy;

    private LocalDateTime modDate;

    private Long parentCommentId;
    
    private Long classInquiryId;

    private Long postId;

    private int likeCount;

    private List<String> likeUsers = new ArrayList<>();
}
