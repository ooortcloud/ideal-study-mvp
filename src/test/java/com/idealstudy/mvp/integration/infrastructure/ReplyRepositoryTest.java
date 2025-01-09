package com.idealstudy.mvp.integration.infrastructure;

import com.idealstudy.mvp.application.dto.ReplyPageResultDto;
import lombok.extern.slf4j.Slf4j;

import java.util.NoSuchElementException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.idealstudy.mvp.TestRepositoryUtil;
import com.idealstudy.mvp.application.dto.ReplyDto;
import com.idealstudy.mvp.enums.classroom.Visibility;
import com.idealstudy.mvp.application.repository.ReplyRepository;

@SpringBootTest
@Transactional
@Slf4j
public class ReplyRepositoryTest {

    private final ReplyRepository replyRepository;

    // 테스트 이외에 의존성 없음
    private final TestRepositoryUtil testRepositoryUtil;
    private Long autoIncrement;
    
    @Autowired
    public ReplyRepositoryTest(ReplyRepository replyRepository, TestRepositoryUtil testRepositoryUtil) {
        this.replyRepository = replyRepository;
        this.testRepositoryUtil = testRepositoryUtil;
    }

    private static final String TEACHER_ID = "98a10847-ad7e-11ef-8e5c-0242ac140002";

    private static final String CLASSROOM_ID = "98a12345-ad7e-11ef-8e5c-0242ac140002";

    private static final String STUDENT_ID = "c99fd58f-b0ae-11ef-89d8-0242ac140003";

    private static final String PARENTS_ID = "c99fd83e-b0ae-11ef-89d8-0242ac140003";

    private static final Long CLASS_INQUIRY_ID = 1L;

    private static final Long POST_ID = 1L;

    private static final Long EXIST_PARENT_ID = 1L;

    private static final Long EXIST_SUB_ID = 2L;

    private static final String TABLE_NAME = "comment"; 

    @BeforeEach
    public void getAutoIncrement() {
        autoIncrement = testRepositoryUtil.getAutoIncrement(TABLE_NAME);
    }

    @Test
    public void create_parent_comment() {

        String content = "test";
        Visibility visibility = Visibility.PUBLIC;
        Long parentCommentId = null;
        Long classInquiryId = CLASS_INQUIRY_ID;
        Long postId = null;
        String createdBy = STUDENT_ID;

        ReplyDto reply = replyRepository.create(content, visibility, parentCommentId, classInquiryId, postId,
                createdBy);

        Assertions.assertThat(reply.getCommentId()).isEqualTo(autoIncrement);
        Assertions.assertThat(reply.getContent()).isEqualTo(content);
        Assertions.assertThat(reply.getVisibility()).isEqualTo(visibility);
        Assertions.assertThat(reply.getParentCommentId()).isEqualTo(parentCommentId);
        Assertions.assertThat(reply.getClassInquiryId()).isEqualTo(classInquiryId);
        Assertions.assertThat(reply.getPostId()).isEqualTo(postId);
        Assertions.assertThat(reply.getCreatedBy()).isEqualTo(createdBy);
    }

    @Test
    public void create_sub_comment() {

        String content = "네네 수고하세요.";
        Visibility visibility = Visibility.PUBLIC;
        Long parentCommentId = EXIST_PARENT_ID;
        Long classInquiryId = CLASS_INQUIRY_ID;
        Long postId = null;
        String createdBy = TEACHER_ID;

        ReplyDto reply = replyRepository.create(content, visibility, parentCommentId, classInquiryId, postId,
                createdBy);

        Assertions.assertThat(reply.getCommentId()).isEqualTo(autoIncrement);
        Assertions.assertThat(reply.getContent()).isEqualTo(content);
        Assertions.assertThat(reply.getVisibility()).isEqualTo(visibility);
        Assertions.assertThat(reply.getParentCommentId()).isEqualTo(parentCommentId);
        Assertions.assertThat(reply.getClassInquiryId()).isEqualTo(classInquiryId);
        Assertions.assertThat(reply.getPostId()).isEqualTo(postId);
        Assertions.assertThat(reply.getCreatedBy()).isEqualTo(createdBy);
    }

    @Test
    public void findById_parent_comment() {
        ReplyDto reply = replyRepository.findById(EXIST_PARENT_ID);

        Assertions.assertThat(reply.getCommentId()).isEqualTo(EXIST_PARENT_ID);
        Assertions.assertThat(reply.getVisibility()).isEqualTo(Visibility.PUBLIC);
        Assertions.assertThat(reply.getParentCommentId()).isEqualTo(null);
        Assertions.assertThat(reply.getClassInquiryId()).isEqualTo(CLASS_INQUIRY_ID);
        Assertions.assertThat(reply.getPostId()).isEqualTo(null);
        Assertions.assertThat(reply.getCreatedBy()).isEqualTo(TEACHER_ID);
    }

    @Test
    public void findById_sub_comment() {
        ReplyDto reply = replyRepository.findById(EXIST_SUB_ID);

        Assertions.assertThat(reply.getCommentId()).isEqualTo(EXIST_SUB_ID);    
        Assertions.assertThat(reply.getVisibility()).isEqualTo(Visibility.PUBLIC);
        Assertions.assertThat(reply.getParentCommentId()).isEqualTo(EXIST_PARENT_ID);
        Assertions.assertThat(reply.getClassInquiryId()).isEqualTo(CLASS_INQUIRY_ID);
        Assertions.assertThat(reply.getPostId()).isEqualTo( null);
        Assertions.assertThat(reply.getCreatedBy()).isEqualTo(STUDENT_ID);
    }

    @Test
    public void findById_LikesTest() {

        ReplyDto reply = replyRepository.findById(EXIST_PARENT_ID);

        // Assertions.assertThat(reply.getLikes().size()).isEqualTo(1);
    }

    @Test
    public void update() {

        String content = "수정된 댓글";
        Visibility visibility = Visibility.PRIVATE;
        Long id = EXIST_SUB_ID;

        ReplyDto reply = replyRepository.update(id, content, visibility);

        Assertions.assertThat(reply.getCommentId()).isEqualTo(EXIST_SUB_ID);
        Assertions.assertThat(reply.getContent()).isEqualTo(content);
        Assertions.assertThat(reply.getVisibility()).isEqualTo(visibility);
    }

    @Test
    public void delete() {
        Long id = EXIST_SUB_ID;

        replyRepository.delete(id);

        Assertions.assertThatThrownBy(() -> replyRepository.findById(id))
        .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void findListInClassInquiry() {

        Long classInquiryId = CLASS_INQUIRY_ID;
        int page = 1;

        ReplyPageResultDto dto = replyRepository.findListInClassInquiry(classInquiryId, page);

        Assertions.assertThat(dto.getDtoList().size()).isNotZero();
        Assertions.assertThat(dto.getTotalPage()).isNotZero();
        Assertions.assertThat(dto.getPage()).isEqualTo(1);
        Assertions.assertThat(dto.getSize()).isEqualTo(10);
        Assertions.assertThat(dto.getStartPage()).isEqualTo(1);
        Assertions.assertThat(dto.getEndPage()).isEqualTo(1);
        Assertions.assertThat(dto.isHasPrev()).isFalse();
        Assertions.assertThat(dto.isHasNext()).isFalse();

        ReplyDto reply = dto.getDtoList().get(0);
        Assertions.assertThat(reply.getCommentId()).isEqualTo(EXIST_PARENT_ID);
        Assertions.assertThat(reply.getVisibility()).isEqualTo(Visibility.PUBLIC);
        Assertions.assertThat(reply.getParentCommentId()).isEqualTo(null);
        Assertions.assertThat(reply.getClassInquiryId()).isEqualTo(CLASS_INQUIRY_ID);
        Assertions.assertThat(reply.getPostId()).isEqualTo(null);
        Assertions.assertThat(reply.getCreatedBy()).isEqualTo(TEACHER_ID);

        ReplyDto reply2 = dto.getDtoList().get(1);
        Assertions.assertThat(reply2.getCommentId()).isEqualTo(EXIST_SUB_ID);
        Assertions.assertThat(reply2.getVisibility()).isEqualTo(Visibility.PUBLIC);
        Assertions.assertThat(reply2.getParentCommentId()).isEqualTo(EXIST_PARENT_ID);
        Assertions.assertThat(reply2.getClassInquiryId()).isEqualTo(CLASS_INQUIRY_ID);
        Assertions.assertThat(reply2.getPostId()).isEqualTo(null);
        Assertions.assertThat(reply2.getCreatedBy()).isEqualTo(STUDENT_ID);
    }

}
