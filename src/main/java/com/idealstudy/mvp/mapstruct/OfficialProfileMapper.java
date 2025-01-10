package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.OfficialProfileDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.OfficialProfileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OfficialProfileMapper {

    OfficialProfileMapper INSTANCE = Mappers.getMapper(OfficialProfileMapper.class);

    @Mapping(source = "teacher.userId", target = "teacherId")
    OfficialProfileDto toDto(OfficialProfileEntity entity);
}
