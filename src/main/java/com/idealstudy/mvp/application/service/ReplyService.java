package com.idealstudy.mvp.application.service;

import com.idealstudy.mvp.application.dto.ReplyDto;
import com.idealstudy.mvp.application.dto.ReplyPageResultDto;
import com.idealstudy.mvp.enums.error.DBErrorMsg;
import com.idealstudy.mvp.enums.error.SecurityErrorMsg;
import com.idealstudy.mvp.enums.classroom.Visibility;
import com.idealstudy.mvp.application.repository.LikedRepository;
import com.idealstudy.mvp.application.repository.ReplyRepository;
import com.idealstudy.mvp.util.TryCatchServiceTemplate;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class ReplyService {

    private final ReplyRepository replyRepository;

    private final LikedRepository likedRepository;

    @Autowired
    public ReplyService(ReplyRepository replyRepository,
                        @Qualifier("likedReplyRepositoryImpl") LikedRepository likedRepository) {
        this.replyRepository = replyRepository;
        this.likedRepository = likedRepository;
    }

    public ReplyDto create(String content, Visibility visibility, Long parentCommentId, Long classInquiryId,
                           Long postId, String createdBy) {

        return TryCatchServiceTemplate.execute(
            () -> replyRepository.create(content, visibility, parentCommentId, classInquiryId, postId, createdBy),
            null,
            DBErrorMsg.CREATE_ERROR
        );
    }

    /**
     * 댓글을 상세조회할 일이 없음.
     * @param commentId
     * @param userId
     * @return
     */
    @Deprecated
    public ReplyDto findById(Long commentId, String userId) {

        return TryCatchServiceTemplate.execute(
            () -> replyRepository.findById(commentId),
            () -> checkMine(commentId, userId),
            DBErrorMsg.SELECT_ERROR
        );
    }

    public ReplyPageResultDto findListInClassInquiry(Long classInquiryId, int page) {

        return TryCatchServiceTemplate.execute(
            () -> replyRepository.findListInClassInquiry(classInquiryId, page),
            null,
            DBErrorMsg.SELECT_ERROR
        );
    }

    public ReplyDto update(Long id, String userId, String content, Visibility visibility) {

        return TryCatchServiceTemplate.execute(
            () -> replyRepository.update(id, content, visibility),
            () -> checkMine(id, userId),
            DBErrorMsg.UPDATE_ERROR
        );
    }

    public void delete(Long id, String userId) {

        TryCatchServiceTemplate.execute(() -> {
            replyRepository.delete(id);
            return null;
        }, ()-> checkMine(id, userId), DBErrorMsg.DELETE_ERROR);
    }

    public void updateLiked(Long likedId, Long replyId, String userId) {

        TryCatchServiceTemplate.execute(() -> {
            if(likedId == null)
                likedRepository.create(replyId, userId);
            else
                likedRepository.delete(likedId, replyId);
            return null;
        }, null, DBErrorMsg.UPDATE_ERROR);
    }

    public int countLiked(Long replyId) {

        String collection = "replies";
        return likedRepository.countById(replyId);
    }

    private void checkMine(Long commentId, String userId) {

        ReplyDto dto = replyRepository.findById(commentId);

        if( !dto.getCreatedBy().equals(userId)) {
            log.error(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
            throw new SecurityException(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
        }
    }
}
