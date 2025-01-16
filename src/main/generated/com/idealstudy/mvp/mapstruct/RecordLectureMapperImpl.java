package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.RecordLectureDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.RecordLecturePageResultDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.RecordLectureEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-15T14:05:43+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class RecordLectureMapperImpl implements RecordLectureMapper {

    @Override
    public RecordLectureDto toDto(RecordLectureEntity entity) {
        if ( entity == null ) {
            return null;
        }

        RecordLectureDto recordLectureDto = new RecordLectureDto();

        recordLectureDto.setClassroomId( entityClassroomClassroomId( entity ) );
        recordLectureDto.setId( entity.getId() );
        recordLectureDto.setOrderNum( entity.getOrderNum() );
        recordLectureDto.setTitle( entity.getTitle() );
        recordLectureDto.setDescription( entity.getDescription() );
        recordLectureDto.setPlaytime( entity.getPlaytime() );
        recordLectureDto.setUrl( entity.getUrl() );
        recordLectureDto.setRegDate( entity.getRegDate() );
        recordLectureDto.setModDate( entity.getModDate() );

        return recordLectureDto;
    }

    @Override
    public RecordLectureEntity toEntity(RecordLectureDto dto) {
        if ( dto == null ) {
            return null;
        }

        RecordLectureEntity.RecordLectureEntityBuilder<?, ?> recordLectureEntity = RecordLectureEntity.builder();

        recordLectureEntity.classroom( recordLectureDtoToClassroomEntity( dto ) );
        recordLectureEntity.regDate( dto.getRegDate() );
        recordLectureEntity.modDate( dto.getModDate() );
        recordLectureEntity.id( dto.getId() );
        recordLectureEntity.orderNum( dto.getOrderNum() );
        recordLectureEntity.title( dto.getTitle() );
        recordLectureEntity.description( dto.getDescription() );
        recordLectureEntity.playtime( dto.getPlaytime() );
        recordLectureEntity.url( dto.getUrl() );

        return recordLectureEntity.build();
    }

    @Override
    public void updateEntity(RecordLectureDto dto, RecordLectureEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getOrderNum() != null ) {
            entity.setOrderNum( dto.getOrderNum() );
        }
        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        entity.setPlaytime( dto.getPlaytime() );
        if ( dto.getUrl() != null ) {
            entity.setUrl( dto.getUrl() );
        }
    }

    @Override
    public RecordLecturePageResultDto toPageResultDto(PageResultDto<RecordLectureDto, RecordLectureEntity> pageResultDto) {
        if ( pageResultDto == null ) {
            return null;
        }

        RecordLecturePageResultDto recordLecturePageResultDto = new RecordLecturePageResultDto();

        List<RecordLectureDto> list = pageResultDto.getDtoList();
        if ( list != null ) {
            recordLecturePageResultDto.setDtoList( new ArrayList<RecordLectureDto>( list ) );
        }
        recordLecturePageResultDto.setTotalPage( pageResultDto.getTotalPage() );
        recordLecturePageResultDto.setPage( pageResultDto.getPage() );
        recordLecturePageResultDto.setSize( pageResultDto.getSize() );
        recordLecturePageResultDto.setStartPage( pageResultDto.getStartPage() );
        recordLecturePageResultDto.setEndPage( pageResultDto.getEndPage() );
        recordLecturePageResultDto.setHasPrev( pageResultDto.isHasPrev() );
        recordLecturePageResultDto.setHasNext( pageResultDto.isHasNext() );
        List<Integer> list1 = pageResultDto.getPageList();
        if ( list1 != null ) {
            recordLecturePageResultDto.setPageList( new ArrayList<Integer>( list1 ) );
        }

        return recordLecturePageResultDto;
    }

    private String entityClassroomClassroomId(RecordLectureEntity recordLectureEntity) {
        if ( recordLectureEntity == null ) {
            return null;
        }
        ClassroomEntity classroom = recordLectureEntity.getClassroom();
        if ( classroom == null ) {
            return null;
        }
        String classroomId = classroom.getClassroomId();
        if ( classroomId == null ) {
            return null;
        }
        return classroomId;
    }

    protected ClassroomEntity recordLectureDtoToClassroomEntity(RecordLectureDto recordLectureDto) {
        if ( recordLectureDto == null ) {
            return null;
        }

        ClassroomEntity.ClassroomEntityBuilder<?, ?> classroomEntity = ClassroomEntity.builder();

        classroomEntity.classroomId( recordLectureDto.getClassroomId() );

        return classroomEntity.build();
    }
}
