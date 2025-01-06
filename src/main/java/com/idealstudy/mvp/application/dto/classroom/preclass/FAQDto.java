package com.idealstudy.mvp.application.dto.classroom.preclass;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class FAQDto {

    private Long id;

    private String classroomId;

    private String title;

    private String content;

    ////////////////////////////////////////////////////////////////////////

    private LocalDateTime regDate; // 생성 일시

    private LocalDateTime modDate; // 수정 일시

    private String createdBy; // 생성자
}
