package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.LikedReplyDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface LikedMapper {

    LikedMapper INSTANCE = Mappers.getMapper(LikedMapper.class);

    LikedReplyDto toDto();
}
