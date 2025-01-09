package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.EnrollmentDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.EnrollmentPageResultDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.EnrollmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EnrollmentMapper {
    
    EnrollmentMapper INSTANCE = Mappers.getMapper(EnrollmentMapper.class);

    @Mapping(target = "classroomId", source = "classroom.classroomId")
    @Mapping(target = "studentId", source = "student.userId")
    EnrollmentDto toDto(EnrollmentEntity entity);

    @Mapping(source = "classroomId", target = "classroom.classroomId")
    @Mapping(source = "studentId", target = "student.userId")
    EnrollmentEntity toEntity(EnrollmentDto dto);

    EnrollmentPageResultDto toPageResultDto(PageResultDto<EnrollmentDto, EnrollmentEntity> page);
}