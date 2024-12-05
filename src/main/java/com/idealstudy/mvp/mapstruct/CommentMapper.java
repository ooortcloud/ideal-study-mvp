package com.idealstudy.mvp.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.idealstudy.mvp.application.dto.ReplyDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ReplyEntity;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
    
    @Mapping(source = "parentComment.commentId", target = "parentCommentId")
    @Mapping(source = "classInquiry.id", target = "classInquiryId")
    @Mapping(source = "post.postId", target = "postId")
    ReplyDto entityToDto(ReplyEntity entity);
    @Mapping(source = "parentCommentId", target = "parentComment.commentId")
    @Mapping(source = "classInquiryId", target = "classInquiry.id")
    @Mapping(source = "postId", target = "post.postId")
    ReplyEntity dtoToEntity(ReplyDto dto);

    

}
