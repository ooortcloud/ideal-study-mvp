package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.EnrollmentDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.EnrollmentPageResultDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.EnrollmentEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.StudentEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-05T23:53:02+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class EnrollmentMapperImpl implements EnrollmentMapper {

    @Override
    public EnrollmentDto toDto(EnrollmentEntity entity) {
        if ( entity == null ) {
            return null;
        }

        EnrollmentDto enrollmentDto = new EnrollmentDto();

        enrollmentDto.setClassroomId( entityClassroomClassroomId( entity ) );
        enrollmentDto.setStudentId( entityStudentUserId( entity ) );
        enrollmentDto.setEnrollmentId( entity.getEnrollmentId() );
        enrollmentDto.setCreatedBy( entity.getCreatedBy() );
        enrollmentDto.setStatus( entity.getStatus() );
        enrollmentDto.setCurScore( entity.getCurScore() );
        enrollmentDto.setTargetScore( entity.getTargetScore() );
        enrollmentDto.setRequest( entity.getRequest() );
        enrollmentDto.setDetermination( entity.getDetermination() );

        return enrollmentDto;
    }

    @Override
    public EnrollmentEntity toEntity(EnrollmentDto dto) {
        if ( dto == null ) {
            return null;
        }

        EnrollmentEntity.EnrollmentEntityBuilder<?, ?> enrollmentEntity = EnrollmentEntity.builder();

        enrollmentEntity.classroom( enrollmentDtoToClassroomEntity( dto ) );
        enrollmentEntity.student( enrollmentDtoToStudentEntity( dto ) );
        enrollmentEntity.createdBy( dto.getCreatedBy() );
        enrollmentEntity.enrollmentId( dto.getEnrollmentId() );
        enrollmentEntity.status( dto.getStatus() );
        enrollmentEntity.curScore( dto.getCurScore() );
        enrollmentEntity.targetScore( dto.getTargetScore() );
        enrollmentEntity.request( dto.getRequest() );
        enrollmentEntity.determination( dto.getDetermination() );

        return enrollmentEntity.build();
    }

    @Override
    public EnrollmentPageResultDto toPageResultDto(PageResultDto<EnrollmentDto, EnrollmentEntity> page) {
        if ( page == null ) {
            return null;
        }

        EnrollmentPageResultDto enrollmentPageResultDto = new EnrollmentPageResultDto();

        List<EnrollmentDto> list = page.getDtoList();
        if ( list != null ) {
            enrollmentPageResultDto.setDtoList( new ArrayList<EnrollmentDto>( list ) );
        }
        enrollmentPageResultDto.setTotalPage( page.getTotalPage() );
        enrollmentPageResultDto.setPage( page.getPage() );
        enrollmentPageResultDto.setSize( page.getSize() );
        enrollmentPageResultDto.setStartPage( page.getStartPage() );
        enrollmentPageResultDto.setEndPage( page.getEndPage() );
        enrollmentPageResultDto.setHasPrev( page.isHasPrev() );
        enrollmentPageResultDto.setHasNext( page.isHasNext() );
        List<Integer> list1 = page.getPageList();
        if ( list1 != null ) {
            enrollmentPageResultDto.setPageList( new ArrayList<Integer>( list1 ) );
        }

        return enrollmentPageResultDto;
    }

    private String entityClassroomClassroomId(EnrollmentEntity enrollmentEntity) {
        if ( enrollmentEntity == null ) {
            return null;
        }
        ClassroomEntity classroom = enrollmentEntity.getClassroom();
        if ( classroom == null ) {
            return null;
        }
        String classroomId = classroom.getClassroomId();
        if ( classroomId == null ) {
            return null;
        }
        return classroomId;
    }

    private String entityStudentUserId(EnrollmentEntity enrollmentEntity) {
        if ( enrollmentEntity == null ) {
            return null;
        }
        StudentEntity student = enrollmentEntity.getStudent();
        if ( student == null ) {
            return null;
        }
        String userId = student.getUserId();
        if ( userId == null ) {
            return null;
        }
        return userId;
    }

    protected ClassroomEntity enrollmentDtoToClassroomEntity(EnrollmentDto enrollmentDto) {
        if ( enrollmentDto == null ) {
            return null;
        }

        ClassroomEntity.ClassroomEntityBuilder<?, ?> classroomEntity = ClassroomEntity.builder();

        classroomEntity.classroomId( enrollmentDto.getClassroomId() );

        return classroomEntity.build();
    }

    protected StudentEntity enrollmentDtoToStudentEntity(EnrollmentDto enrollmentDto) {
        if ( enrollmentDto == null ) {
            return null;
        }

        StudentEntity.StudentEntityBuilder<?, ?> studentEntity = StudentEntity.builder();

        studentEntity.userId( enrollmentDto.getStudentId() );

        return studentEntity.build();
    }
}
