package com.idealstudy.mvp.application.repository;


import com.idealstudy.mvp.application.dto.ReplyDto;
import com.idealstudy.mvp.application.dto.ReplyPageResultDto;
import com.idealstudy.mvp.enums.classroom.Visibility;

public interface ReplyRepository {

    ReplyDto create(String content, Visibility visibility, Long parentCommentId, Long classInquiryId, 
        Long postId, String createdBy);

    ReplyPageResultDto findListInClassInquiry(Long classInquiryId, int page);

    ReplyDto findById(Long commentId);

    ReplyDto update(Long id, String content, Visibility visibility);

    void delete(Long commentId);
}
