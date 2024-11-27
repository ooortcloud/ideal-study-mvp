package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.application.dto.member.MemberPageResultDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.MemberEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

// unmappedTargetPolicy = ReportingPolicy.IGNORE: target class에 매핑되지 않는 필드가 있으면, null로 채운 후 따로 report하지 않음.
// nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE: null 필드는 업데이트하지 않음.
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    MemberDto entityToDto(MemberEntity entity);
    MemberEntity dtoToEntity(MemberDto dto);

    // @MappingTarget: 반환 대상 객체 지정
    @Mapping(target = "userId", ignore = true)
    void updateEntityFromDto(MemberDto dto, @MappingTarget MemberEntity entity);

    MemberPageResultDto toApplicationPageResult(PageResultDto<MemberDto, MemberEntity> pageResultDto);
}
