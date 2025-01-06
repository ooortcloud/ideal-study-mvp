package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.classroom.inclass.AttendanceDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.AttendanceEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-06T10:21:01+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class AttendanceMapperImpl implements AttendanceMapper {

    @Override
    public AttendanceDto toDto(AttendanceEntity entity) {
        if ( entity == null ) {
            return null;
        }

        AttendanceDto attendanceDto = new AttendanceDto();

        attendanceDto.setClassroomId( entityClassroomClassroomId( entity ) );
        attendanceDto.setId( entity.getId() );
        attendanceDto.setCreatedBy( entity.getCreatedBy() );
        attendanceDto.setRegDate( entity.getRegDate() );
        attendanceDto.setCheckOutDate( entity.getCheckOutDate() );

        return attendanceDto;
    }

    private String entityClassroomClassroomId(AttendanceEntity attendanceEntity) {
        if ( attendanceEntity == null ) {
            return null;
        }
        ClassroomEntity classroom = attendanceEntity.getClassroom();
        if ( classroom == null ) {
            return null;
        }
        String classroomId = classroom.getClassroomId();
        if ( classroomId == null ) {
            return null;
        }
        return classroomId;
    }
}
