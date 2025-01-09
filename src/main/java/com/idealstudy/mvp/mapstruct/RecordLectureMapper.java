package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.RecordLectureDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.RecordLecturePageResultDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.RecordLectureEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RecordLectureMapper {

    RecordLectureMapper INSTANCE = Mappers.getMapper(RecordLectureMapper.class);

    @Mapping(source = "classroom.classroomId", target = "classroomId")
    RecordLectureDto toDto(RecordLectureEntity entity);

    @Mapping(source = "classroomId", target = "classroom.classroomId")
    RecordLectureEntity toEntity(RecordLectureDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntity(RecordLectureDto dto, @MappingTarget RecordLectureEntity entity);

    RecordLecturePageResultDto toPageResultDto(PageResultDto<RecordLectureDto, RecordLectureEntity> pageResultDto);
}
