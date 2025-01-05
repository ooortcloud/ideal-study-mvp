package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.LiveLectureDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.LiveLecturePageResultDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.LiveLectureEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-05T23:53:01+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class LiveLectureMapperImpl implements LiveLectureMapper {

    @Override
    public LiveLectureDto toDto(LiveLectureEntity entity) {
        if ( entity == null ) {
            return null;
        }

        LiveLectureDto liveLectureDto = new LiveLectureDto();

        liveLectureDto.setClassroomId( entityClassroomClassroomId( entity ) );
        liveLectureDto.setId( entity.getId() );
        liveLectureDto.setTitle( entity.getTitle() );
        liveLectureDto.setStartTime( entity.getStartTime() );
        liveLectureDto.setEndTime( entity.getEndTime() );
        liveLectureDto.setStudySpace( entity.getStudySpace() );
        liveLectureDto.setPlatform( entity.getPlatform() );

        return liveLectureDto;
    }

    @Override
    public LiveLectureEntity toEntity(LiveLectureDto dto) {
        if ( dto == null ) {
            return null;
        }

        LiveLectureEntity.LiveLectureEntityBuilder<?, ?> liveLectureEntity = LiveLectureEntity.builder();

        liveLectureEntity.classroom( liveLectureDtoToClassroomEntity( dto ) );
        liveLectureEntity.id( dto.getId() );
        liveLectureEntity.title( dto.getTitle() );
        liveLectureEntity.startTime( dto.getStartTime() );
        liveLectureEntity.endTime( dto.getEndTime() );
        liveLectureEntity.studySpace( dto.getStudySpace() );
        liveLectureEntity.platform( dto.getPlatform() );

        return liveLectureEntity.build();
    }

    @Override
    public LiveLecturePageResultDto toPageResultDto(PageResultDto<LiveLectureDto, LiveLectureEntity> pageResultDto) {
        if ( pageResultDto == null ) {
            return null;
        }

        LiveLecturePageResultDto liveLecturePageResultDto = new LiveLecturePageResultDto();

        List<LiveLectureDto> list = pageResultDto.getDtoList();
        if ( list != null ) {
            liveLecturePageResultDto.setDtoList( new ArrayList<LiveLectureDto>( list ) );
        }
        liveLecturePageResultDto.setTotalPage( pageResultDto.getTotalPage() );
        liveLecturePageResultDto.setPage( pageResultDto.getPage() );
        liveLecturePageResultDto.setSize( pageResultDto.getSize() );
        liveLecturePageResultDto.setStartPage( pageResultDto.getStartPage() );
        liveLecturePageResultDto.setEndPage( pageResultDto.getEndPage() );
        liveLecturePageResultDto.setHasPrev( pageResultDto.isHasPrev() );
        liveLecturePageResultDto.setHasNext( pageResultDto.isHasNext() );
        List<Integer> list1 = pageResultDto.getPageList();
        if ( list1 != null ) {
            liveLecturePageResultDto.setPageList( new ArrayList<Integer>( list1 ) );
        }

        return liveLecturePageResultDto;
    }

    private String entityClassroomClassroomId(LiveLectureEntity liveLectureEntity) {
        if ( liveLectureEntity == null ) {
            return null;
        }
        ClassroomEntity classroom = liveLectureEntity.getClassroom();
        if ( classroom == null ) {
            return null;
        }
        String classroomId = classroom.getClassroomId();
        if ( classroomId == null ) {
            return null;
        }
        return classroomId;
    }

    protected ClassroomEntity liveLectureDtoToClassroomEntity(LiveLectureDto liveLectureDto) {
        if ( liveLectureDto == null ) {
            return null;
        }

        ClassroomEntity.ClassroomEntityBuilder<?, ?> classroomEntity = ClassroomEntity.builder();

        classroomEntity.classroomId( liveLectureDto.getClassroomId() );

        return classroomEntity.build();
    }
}
