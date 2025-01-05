package com.idealstudy.mvp.unit;

import com.idealstudy.mvp.application.dto.ReplyDto;
import com.idealstudy.mvp.application.dto.ReplyPageResultDto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.idealstudy.mvp.integration.infrastructure.util.TestRepositoryUtil;
import com.idealstudy.mvp.application.service.ReplyService;
import com.idealstudy.mvp.enums.classroom.Visibility;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class ReplyServiceTest {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private TestRepositoryUtil testRepositoryUtil;
    private Long autoIncrement;
    private Long likesAutoImcrement;

    private static final String TEACHER_ID = "98a10847-ad7e-11ef-8e5c-0242ac140002";

    private static final String CLASSROOM_ID = "98a12345-ad7e-11ef-8e5c-0242ac140002";

    private static final String STUDENT_ID = "c99fd58f-b0ae-11ef-89d8-0242ac140003";

    private static final String PARENTS_ID = "c99fd83e-b0ae-11ef-89d8-0242ac140003";

    private static final Long CLASS_INQUIRY_ID = 1L;

    private static final Long POST_ID = 1L;

    private static final Long EXIST_PARENT_ID = 1L;

    private static final Long EXIST_SUB_ID = 2L;

    private static final Long EXIST_PRIVATE_ID = 3L;

    private static final String TABLE_NAME = "comment"; 

    @BeforeEach
    public void setUp() {
        autoIncrement = testRepositoryUtil.getAutoIncrement(TABLE_NAME);
        likesAutoImcrement = testRepositoryUtil.getAutoIncrement("liked");
    }

    @Test
    public void createInClassInquiry() {

        String content = "content";
        Visibility visibility = Visibility.PUBLIC;
        Long parentCommentId = EXIST_PARENT_ID;
        Long classInquiryId = CLASS_INQUIRY_ID;
        String createdBy = TEACHER_ID;
        Long postId = null;

        ReplyDto replyDto = replyService.create(content, visibility, parentCommentId, classInquiryId, postId, createdBy);
        Assertions.assertThat(replyDto.getCommentId()).isEqualTo(autoIncrement);
        Assertions.assertThat(replyDto.getContent()).isEqualTo(content);
        Assertions.assertThat(replyDto.getVisibility()).isEqualTo(visibility);
        Assertions.assertThat(replyDto.getParentCommentId()).isEqualTo(parentCommentId);
        Assertions.assertThat(replyDto.getClassInquiryId()).isEqualTo(classInquiryId);
        Assertions.assertThat(replyDto.getPostId()).isEqualTo(postId);
        Assertions.assertThat(replyDto.getCreatedBy()).isEqualTo(createdBy);
    }

    @Test
    public void selectInClassInquiry() {

        Long commentId = EXIST_PARENT_ID;
        String userId = TEACHER_ID;

        ReplyDto replyDto = replyService.findById(commentId, userId);
        Assertions.assertThat(replyDto.getCommentId()).isEqualTo(commentId);
        Assertions.assertThat(replyDto.getVisibility()).isEqualTo(Visibility.PUBLIC);
        Assertions.assertThat(replyDto.getParentCommentId()).isEqualTo(null);
        Assertions.assertThat(replyDto.getClassInquiryId()).isEqualTo(CLASS_INQUIRY_ID);
        Assertions.assertThat(replyDto.getPostId()).isEqualTo(null);
        Assertions.assertThat(replyDto.getCreatedBy()).isEqualTo(TEACHER_ID);
    }

    @Test
    public void selectInPost() {

    }

    @Test
    public void selectPrivate() {

        Long commentId = EXIST_PRIVATE_ID;
        String userId = PARENTS_ID;

        Assertions.assertThatThrownBy(() -> replyService.findById(commentId, userId))
            .isInstanceOf(SecurityException.class);
    }

    @Test
    public void selectListInClassInquiry() {

        int page = 1;

        ReplyPageResultDto replyPageResultDto = replyService.findListInClassInquiry(CLASS_INQUIRY_ID, page);
        Assertions.assertThat(replyPageResultDto.getDtoList().size()).isNotZero();
        Assertions.assertThat(replyPageResultDto.getTotalPage()).isEqualTo(1);
        Assertions.assertThat(replyPageResultDto.getPage()).isEqualTo(page);
        Assertions.assertThat(replyPageResultDto.getDtoList().get(0).getCommentId()).isEqualTo(EXIST_PARENT_ID);
        Assertions.assertThat(replyPageResultDto.getDtoList().get(0).getVisibility()).isEqualTo(Visibility.PUBLIC);
        Assertions.assertThat(replyPageResultDto.getDtoList().get(0).getParentCommentId()).isEqualTo(null);
        Assertions.assertThat(replyPageResultDto.getDtoList().get(0).getClassInquiryId()).isEqualTo(CLASS_INQUIRY_ID);
        Assertions.assertThat(replyPageResultDto.getDtoList().get(0).getPostId()).isEqualTo(null);
        Assertions.assertThat(replyPageResultDto.getDtoList().get(0).getCreatedBy()).isEqualTo(TEACHER_ID);

        Assertions.assertThat(replyPageResultDto.getDtoList().get(1).getCommentId()).isEqualTo(EXIST_SUB_ID);
        Assertions.assertThat(replyPageResultDto.getDtoList().get(1).getVisibility()).isEqualTo(Visibility.PUBLIC);
        Assertions.assertThat(replyPageResultDto.getDtoList().get(1).getParentCommentId()).isEqualTo(EXIST_PARENT_ID);
        Assertions.assertThat(replyPageResultDto.getDtoList().get(1).getClassInquiryId()).isEqualTo(CLASS_INQUIRY_ID);
        Assertions.assertThat(replyPageResultDto.getDtoList().get(1).getPostId()).isEqualTo(null);
        Assertions.assertThat(replyPageResultDto.getDtoList().get(1).getCreatedBy()).isEqualTo(STUDENT_ID);
    }

    @Test
    public void update() {

        Long id = EXIST_PARENT_ID;
        String userId = TEACHER_ID;
        String content = "update";
        Visibility visibility = Visibility.PRIVATE;

        ReplyDto replyDto = replyService.update(id, userId, content, visibility);
        Assertions.assertThat(replyDto.getCommentId()).isEqualTo(id);
        Assertions.assertThat(replyDto.getContent()).isEqualTo(content);
        Assertions.assertThat(replyDto.getVisibility()).isEqualTo(visibility);
    }

    @Test
    public void deleteParent() {

        Long id = EXIST_PARENT_ID;
        String userId = TEACHER_ID;

        replyService.delete(id, userId);

        Assertions.assertThatThrownBy(() -> replyService.findById(id, userId))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void deleteSub() {

        Long id = EXIST_SUB_ID;
        String userId = STUDENT_ID;

        replyService.delete(id, userId);

        Assertions.assertThatThrownBy(() -> replyService.findById(id, userId))
            .isInstanceOf(RuntimeException.class);
    }


    @Test
    public void likesTest() {

        // Long likedId = 11L;
        Long likedId = null;
        Long replyId = EXIST_PARENT_ID;
        // String userId = STUDENT_ID;
        String userId = TEACHER_ID;

        replyService.updateLiked(likedId, replyId, userId);

        int count = replyService.countLiked(replyId);
        // Assertions.assertThat(count).isEqualTo(0);
        Assertions.assertThat(count).isEqualTo(2);
    }
}
