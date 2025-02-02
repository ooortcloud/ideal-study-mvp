package com.idealstudy.mvp.application.dto;

import com.idealstudy.mvp.application.dto.member.MemberListDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class LikedReplyPageResultDto {

    /*
    - targetId (클래스랑 댓글 type이 달라서 분리해야 하는데, 인터페이스 논의 비용 줄이려면 그냥 따로 만드는 것이 서로 편할듯)
    - userId 목록
    - 그 외 각종 페이지 정보
     */
    private Long replyId;

    private List<MemberListDto> users;

    private int totalPage;

    private int page;

    private int size;

    private int startPage, endPage;

    private boolean hasPrev, hasNext;

    private List<Integer> pageList;
}
