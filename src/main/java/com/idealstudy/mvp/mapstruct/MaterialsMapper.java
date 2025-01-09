package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.MaterialsDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.MaterialsPageResultDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.MaterialsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MaterialsMapper {

    MaterialsMapper INSTANCE = Mappers.getMapper(MaterialsMapper.class);

    @Mapping(source = "classroom.classroomId", target = "classroomId")
    @Mapping(source = "student.userId", target = "studentId")
    MaterialsDto toDto(MaterialsEntity entity);

    MaterialsPageResultDto toPageResultDto(PageResultDto<MaterialsDto, MaterialsEntity> pageResultDto);
}
