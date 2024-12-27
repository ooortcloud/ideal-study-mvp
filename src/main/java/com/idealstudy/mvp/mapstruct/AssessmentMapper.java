package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.exam.*;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.AssessmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AssessmentMapper {

    AssessmentMapper INSTANCE = Mappers.getMapper(AssessmentMapper.class);

    @Mapping(source = "classroom.classroomId", target = "classroomId")
    @Mapping(source = "classroom.title", target = "classroomName")
    ExamDto toExamDto(AssessmentEntity entity);

    @Mapping(source = "classroom.classroomId", target = "classroomId")
    ExamListResponseDto toExamListResponseDto(AssessmentEntity entity);

    @Mapping(source = "classroom.classroomId", target = "classroomId")
    ExamFeedbackListResponseDto toExamFeedbackListResponseDto(AssessmentEntity entity);

    @Mapping(source = "classroom.classroomId", target = "classroomId")
    ExamFeedbackDetailResponse toExamFeedbackDetailResponse(AssessmentEntity entity);

    ExamPageResultDto<ExamListResponseDto> toPageResultDtoWithExamListResponseDto(
            PageResultDto<ExamListResponseDto, AssessmentEntity> pageResultDto);

    ExamPageResultDto<ExamFeedbackListResponseDto> toPageResultDtoWithExamFeedbackListResponseDto(
            PageResultDto<ExamFeedbackListResponseDto, AssessmentEntity> pageResultDto
    );
}
