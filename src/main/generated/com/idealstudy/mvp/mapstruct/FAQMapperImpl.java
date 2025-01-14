package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.FAQDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.FAQPageResultDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.preclass.FAQEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-12T11:32:40+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class FAQMapperImpl implements FAQMapper {

    @Override
    public FAQDto entityToDto(FAQEntity entity) {
        if ( entity == null ) {
            return null;
        }

        FAQDto.FAQDtoBuilder fAQDto = FAQDto.builder();

        fAQDto.classroomId( entityClassroomClassroomId( entity ) );
        fAQDto.id( entity.getId() );
        fAQDto.title( entity.getTitle() );
        fAQDto.content( entity.getContent() );
        fAQDto.regDate( entity.getRegDate() );
        fAQDto.modDate( entity.getModDate() );
        fAQDto.createdBy( entity.getCreatedBy() );

        return fAQDto.build();
    }

    @Override
    public FAQEntity dtoTOEntity(FAQDto dto) {
        if ( dto == null ) {
            return null;
        }

        FAQEntity.FAQEntityBuilder<?, ?> fAQEntity = FAQEntity.builder();

        fAQEntity.classroom( fAQDtoToClassroomEntity( dto ) );
        fAQEntity.regDate( dto.getRegDate() );
        fAQEntity.modDate( dto.getModDate() );
        fAQEntity.createdBy( dto.getCreatedBy() );
        fAQEntity.id( dto.getId() );
        fAQEntity.title( dto.getTitle() );
        fAQEntity.content( dto.getContent() );

        return fAQEntity.build();
    }

    @Override
    public FAQPageResultDto toPageResultDto(PageResultDto<FAQDto, FAQEntity> dto) {
        if ( dto == null ) {
            return null;
        }

        FAQPageResultDto fAQPageResultDto = new FAQPageResultDto();

        List<FAQDto> list = dto.getDtoList();
        if ( list != null ) {
            fAQPageResultDto.setDtoList( new ArrayList<FAQDto>( list ) );
        }
        fAQPageResultDto.setTotalPage( dto.getTotalPage() );
        fAQPageResultDto.setPage( dto.getPage() );
        fAQPageResultDto.setSize( dto.getSize() );
        fAQPageResultDto.setStartPage( dto.getStartPage() );
        fAQPageResultDto.setEndPage( dto.getEndPage() );
        fAQPageResultDto.setHasPrev( dto.isHasPrev() );
        fAQPageResultDto.setHasNext( dto.isHasNext() );
        List<Integer> list1 = dto.getPageList();
        if ( list1 != null ) {
            fAQPageResultDto.setPageList( new ArrayList<Integer>( list1 ) );
        }

        return fAQPageResultDto;
    }

    private String entityClassroomClassroomId(FAQEntity fAQEntity) {
        if ( fAQEntity == null ) {
            return null;
        }
        ClassroomEntity classroom = fAQEntity.getClassroom();
        if ( classroom == null ) {
            return null;
        }
        String classroomId = classroom.getClassroomId();
        if ( classroomId == null ) {
            return null;
        }
        return classroomId;
    }

    protected ClassroomEntity fAQDtoToClassroomEntity(FAQDto fAQDto) {
        if ( fAQDto == null ) {
            return null;
        }

        ClassroomEntity.ClassroomEntityBuilder<?, ?> classroomEntity = ClassroomEntity.builder();

        classroomEntity.classroomId( fAQDto.getClassroomId() );

        return classroomEntity.build();
    }
}
