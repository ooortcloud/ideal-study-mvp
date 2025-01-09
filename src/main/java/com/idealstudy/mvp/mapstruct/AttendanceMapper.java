package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.classroom.inclass.AttendanceDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.AttendanceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AttendanceMapper {

    AttendanceMapper INSTANCE = Mappers.getMapper(AttendanceMapper.class);

    @Mapping(source = "classroom.classroomId", target = "classroomId")
    AttendanceDto toDto(AttendanceEntity entity);
}
