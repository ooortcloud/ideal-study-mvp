package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.ClassInquiryDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.ClassInquiryPageResultDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.preclass.ClassInquiryEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-04T11:12:27+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class ClassInquiryMapperImpl implements ClassInquiryMapper {

    @Override
    public ClassInquiryDto entityToDto(ClassInquiryEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ClassInquiryDto.ClassInquiryDtoBuilder classInquiryDto = ClassInquiryDto.builder();

        classInquiryDto.classroomId( entityClassroomClassroomId( entity ) );
        classInquiryDto.id( entity.getId() );
        classInquiryDto.title( entity.getTitle() );
        classInquiryDto.content( entity.getContent() );
        classInquiryDto.visibility( entity.getVisibility() );
        classInquiryDto.createdBy( entity.getCreatedBy() );
        classInquiryDto.regDate( entity.getRegDate() );
        classInquiryDto.modDate( entity.getModDate() );

        return classInquiryDto.build();
    }

    @Override
    public ClassInquiryEntity dtoToEntity(ClassInquiryDto dto) {
        if ( dto == null ) {
            return null;
        }

        ClassInquiryEntity.ClassInquiryEntityBuilder<?, ?> classInquiryEntity = ClassInquiryEntity.builder();

        classInquiryEntity.classroom( classInquiryDtoToClassroomEntity( dto ) );
        classInquiryEntity.regDate( dto.getRegDate() );
        classInquiryEntity.modDate( dto.getModDate() );
        classInquiryEntity.createdBy( dto.getCreatedBy() );
        classInquiryEntity.id( dto.getId() );
        classInquiryEntity.title( dto.getTitle() );
        classInquiryEntity.content( dto.getContent() );
        classInquiryEntity.visibility( dto.getVisibility() );

        return classInquiryEntity.build();
    }

    @Override
    public void updateEntity(ClassInquiryDto dto, ClassInquiryEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( dto.getContent() != null ) {
            entity.setContent( dto.getContent() );
        }
        if ( dto.getVisibility() != null ) {
            entity.setVisibility( dto.getVisibility() );
        }
    }

    @Override
    public ClassInquiryPageResultDto toPageResultDto(PageResultDto<ClassInquiryDto, ClassInquiryEntity> dto) {
        if ( dto == null ) {
            return null;
        }

        ClassInquiryPageResultDto classInquiryPageResultDto = new ClassInquiryPageResultDto();

        List<ClassInquiryDto> list = dto.getDtoList();
        if ( list != null ) {
            classInquiryPageResultDto.setDtoList( new ArrayList<ClassInquiryDto>( list ) );
        }
        classInquiryPageResultDto.setTotalPage( dto.getTotalPage() );
        classInquiryPageResultDto.setPage( dto.getPage() );
        classInquiryPageResultDto.setSize( dto.getSize() );
        classInquiryPageResultDto.setStartPage( dto.getStartPage() );
        classInquiryPageResultDto.setEndPage( dto.getEndPage() );
        classInquiryPageResultDto.setHasPrev( dto.isHasPrev() );
        classInquiryPageResultDto.setHasNext( dto.isHasNext() );
        List<Integer> list1 = dto.getPageList();
        if ( list1 != null ) {
            classInquiryPageResultDto.setPageList( new ArrayList<Integer>( list1 ) );
        }

        return classInquiryPageResultDto;
    }

    private String entityClassroomClassroomId(ClassInquiryEntity classInquiryEntity) {
        if ( classInquiryEntity == null ) {
            return null;
        }
        ClassroomEntity classroom = classInquiryEntity.getClassroom();
        if ( classroom == null ) {
            return null;
        }
        String classroomId = classroom.getClassroomId();
        if ( classroomId == null ) {
            return null;
        }
        return classroomId;
    }

    protected ClassroomEntity classInquiryDtoToClassroomEntity(ClassInquiryDto classInquiryDto) {
        if ( classInquiryDto == null ) {
            return null;
        }

        ClassroomEntity.ClassroomEntityBuilder<?, ?> classroomEntity = ClassroomEntity.builder();

        classroomEntity.classroomId( classInquiryDto.getClassroomId() );

        return classroomEntity.build();
    }
}
