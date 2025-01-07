package com.idealstudy.mvp.application.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LikedReplyDto {
    
    /*
        LikedReplyDto 어떤 값을 서비스에 제공해야 하는가?
        - reply id
        - 댓글 좋아요 수

        좋아요 유저 목록 조회는 따로 처리해야 한다.
     */
    
    private Long replyId;
    private Integer count;
}