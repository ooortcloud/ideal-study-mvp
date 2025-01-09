package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.FAQDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.FAQPageResultDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.preclass.FAQEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FAQMapper {

    FAQMapper INSTANCE = Mappers.getMapper(FAQMapper.class);

    @Mapping(target = "classroomId", source = "classroom.classroomId")
    FAQDto entityToDto(FAQEntity entity);
    @Mapping(source = "classroomId", target = "classroom.classroomId")
    FAQEntity dtoTOEntity(FAQDto dto);

    FAQPageResultDto toPageResultDto(PageResultDto<FAQDto, FAQEntity> dto);
}
