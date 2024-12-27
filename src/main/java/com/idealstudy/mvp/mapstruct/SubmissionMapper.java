package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.submission.SubmissionDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.submission.SubmissionFeedbackListResponseDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.submission.SubmissionListResponseDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.submission.SubmissionPageResultDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.SubmissionEntity;
import com.idealstudy.mvp.presentation.dto.classroom.SubmissionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SubmissionMapper {

    SubmissionMapper INSTANCE = Mappers.getMapper(SubmissionMapper.class);

    @Mapping(source = "student.userId", target = "studentId")
    @Mapping(source = "assessment.id", target = "assessmentId")
    @Mapping(source = "student.name", target = "studentName")
    @Mapping(source = "assessment.title", target = "assessmentTitle")
    SubmissionDto toDto(SubmissionEntity entity);

    SubmissionResponseDto toResponseDto(SubmissionDto dto);

    @Mapping(source = "student.userId", target = "studentId")
    @Mapping(source = "student.name", target = "studentName")
    @Mapping(source = "assessment.title", target = "title")
    SubmissionListResponseDto toListResponseDto(SubmissionEntity entity);

    @Mapping(source = "student.userId", target = "studentId")
    @Mapping(source = "assessment.id", target = "assessmentId")
    @Mapping(source = "assessment.title", target = "assessmentTitle")
    @Mapping(source = "assessment.classroom.title", target = "classroomName")
    SubmissionFeedbackListResponseDto toFeedbackListResponseDto(SubmissionEntity entity);



    SubmissionPageResultDto<SubmissionListResponseDto> toPageResultDtoWithSubmissionListResponseDto(
            PageResultDto<SubmissionListResponseDto, SubmissionEntity> pageResultDto);

    SubmissionPageResultDto<SubmissionFeedbackListResponseDto> toPageResultDtoWithSubmissionFeedbackListResponseDto(
            PageResultDto<SubmissionFeedbackListResponseDto, SubmissionEntity> pageResultDto);
}
