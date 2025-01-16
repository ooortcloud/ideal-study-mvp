package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.OfficialProfileDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.OfficialProfileEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.TeacherEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-15T14:05:42+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class OfficialProfileMapperImpl implements OfficialProfileMapper {

    @Override
    public OfficialProfileDto toDto(OfficialProfileEntity entity) {
        if ( entity == null ) {
            return null;
        }

        OfficialProfileDto.OfficialProfileDtoBuilder officialProfileDto = OfficialProfileDto.builder();

        officialProfileDto.teacherId( entityTeacherUserId( entity ) );
        officialProfileDto.content( entity.getContent() );

        return officialProfileDto.build();
    }

    private String entityTeacherUserId(OfficialProfileEntity officialProfileEntity) {
        if ( officialProfileEntity == null ) {
            return null;
        }
        TeacherEntity teacher = officialProfileEntity.getTeacher();
        if ( teacher == null ) {
            return null;
        }
        String userId = teacher.getUserId();
        if ( userId == null ) {
            return null;
        }
        return userId;
    }
}
