package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.ReplyDto;
import com.idealstudy.mvp.application.dto.ReplyPageResultDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.ReplyEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.PostEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.preclass.ClassInquiryEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-06T10:21:02+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class ReplyMapperImpl implements ReplyMapper {

    @Override
    public ReplyDto entityToDto(ReplyEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ReplyDto.ReplyDtoBuilder replyDto = ReplyDto.builder();

        replyDto.parentCommentId( entityParentCommentCommentId( entity ) );
        replyDto.classInquiryId( entityClassInquiryId( entity ) );
        replyDto.postId( entityPostPostId( entity ) );
        replyDto.commentId( entity.getCommentId() );
        replyDto.content( entity.getContent() );
        replyDto.visibility( entity.getVisibility() );
        replyDto.createdBy( entity.getCreatedBy() );
        replyDto.modDate( entity.getModDate() );

        return replyDto.build();
    }

    @Override
    public ReplyEntity dtoToEntity(ReplyDto dto) {
        if ( dto == null ) {
            return null;
        }

        ReplyEntity.ReplyEntityBuilder<?, ?> replyEntity = ReplyEntity.builder();

        replyEntity.parentComment( replyDtoToReplyEntity( dto ) );
        replyEntity.classInquiry( replyDtoToClassInquiryEntity( dto ) );
        replyEntity.post( replyDtoToPostEntity( dto ) );
        replyEntity.modDate( dto.getModDate() );
        replyEntity.createdBy( dto.getCreatedBy() );
        replyEntity.commentId( dto.getCommentId() );
        replyEntity.content( dto.getContent() );
        replyEntity.visibility( dto.getVisibility() );

        return replyEntity.build();
    }

    @Override
    public ReplyPageResultDto toPageResult(PageResultDto<ReplyDto, ReplyEntity> page) {
        if ( page == null ) {
            return null;
        }

        ReplyPageResultDto replyPageResultDto = new ReplyPageResultDto();

        List<ReplyDto> list = page.getDtoList();
        if ( list != null ) {
            replyPageResultDto.setDtoList( new ArrayList<ReplyDto>( list ) );
        }
        replyPageResultDto.setTotalPage( page.getTotalPage() );
        replyPageResultDto.setPage( page.getPage() );
        replyPageResultDto.setSize( page.getSize() );
        replyPageResultDto.setStartPage( page.getStartPage() );
        replyPageResultDto.setEndPage( page.getEndPage() );
        replyPageResultDto.setHasPrev( page.isHasPrev() );
        replyPageResultDto.setHasNext( page.isHasNext() );
        List<Integer> list1 = page.getPageList();
        if ( list1 != null ) {
            replyPageResultDto.setPageList( new ArrayList<Integer>( list1 ) );
        }

        return replyPageResultDto;
    }

    private Long entityParentCommentCommentId(ReplyEntity replyEntity) {
        if ( replyEntity == null ) {
            return null;
        }
        ReplyEntity parentComment = replyEntity.getParentComment();
        if ( parentComment == null ) {
            return null;
        }
        Long commentId = parentComment.getCommentId();
        if ( commentId == null ) {
            return null;
        }
        return commentId;
    }

    private Long entityClassInquiryId(ReplyEntity replyEntity) {
        if ( replyEntity == null ) {
            return null;
        }
        ClassInquiryEntity classInquiry = replyEntity.getClassInquiry();
        if ( classInquiry == null ) {
            return null;
        }
        Long id = classInquiry.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityPostPostId(ReplyEntity replyEntity) {
        if ( replyEntity == null ) {
            return null;
        }
        PostEntity post = replyEntity.getPost();
        if ( post == null ) {
            return null;
        }
        Long postId = post.getPostId();
        if ( postId == null ) {
            return null;
        }
        return postId;
    }

    protected ReplyEntity replyDtoToReplyEntity(ReplyDto replyDto) {
        if ( replyDto == null ) {
            return null;
        }

        ReplyEntity.ReplyEntityBuilder<?, ?> replyEntity = ReplyEntity.builder();

        replyEntity.commentId( replyDto.getParentCommentId() );

        return replyEntity.build();
    }

    protected ClassInquiryEntity replyDtoToClassInquiryEntity(ReplyDto replyDto) {
        if ( replyDto == null ) {
            return null;
        }

        ClassInquiryEntity.ClassInquiryEntityBuilder<?, ?> classInquiryEntity = ClassInquiryEntity.builder();

        classInquiryEntity.id( replyDto.getClassInquiryId() );

        return classInquiryEntity.build();
    }

    protected PostEntity replyDtoToPostEntity(ReplyDto replyDto) {
        if ( replyDto == null ) {
            return null;
        }

        PostEntity.PostEntityBuilder<?, ?> postEntity = PostEntity.builder();

        postEntity.postId( replyDto.getPostId() );

        return postEntity.build();
    }
}
