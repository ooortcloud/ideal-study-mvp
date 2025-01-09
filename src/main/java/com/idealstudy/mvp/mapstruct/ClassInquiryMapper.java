package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.ClassInquiryDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.ClassInquiryPageResultDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.preclass.ClassInquiryEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ClassInquiryMapper {

    ClassInquiryMapper INSTANCE = Mappers.getMapper(ClassInquiryMapper.class);

    @Mapping(target = "classroomId", source = "classroom.classroomId")
    ClassInquiryDto entityToDto(ClassInquiryEntity entity);

    @Mapping(source = "classroomId", target = "classroom.classroomId")
    ClassInquiryEntity dtoToEntity(ClassInquiryDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntity(ClassInquiryDto dto, @MappingTarget ClassInquiryEntity entity);

    ClassInquiryPageResultDto toPageResultDto(PageResultDto<ClassInquiryDto, ClassInquiryEntity> dto);
}
