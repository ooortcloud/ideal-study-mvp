package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.MaterialsDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.MaterialsPageResultDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.MaterialsEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.StudentEntity;
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
public class MaterialsMapperImpl implements MaterialsMapper {

    @Override
    public MaterialsDto toDto(MaterialsEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MaterialsDto materialsDto = new MaterialsDto();

        materialsDto.setClassroomId( entityClassroomClassroomId( entity ) );
        materialsDto.setStudentId( entityStudentUserId( entity ) );
        materialsDto.setId( entity.getId() );
        materialsDto.setOrderNum( entity.getOrderNum() );
        materialsDto.setStatus( entity.getStatus() );
        materialsDto.setTitle( entity.getTitle() );
        materialsDto.setDescription( entity.getDescription() );
        materialsDto.setMaterialUri( entity.getMaterialUri() );

        return materialsDto;
    }

    @Override
    public MaterialsPageResultDto toPageResultDto(PageResultDto<MaterialsDto, MaterialsEntity> pageResultDto) {
        if ( pageResultDto == null ) {
            return null;
        }

        MaterialsPageResultDto materialsPageResultDto = new MaterialsPageResultDto();

        List<MaterialsDto> list = pageResultDto.getDtoList();
        if ( list != null ) {
            materialsPageResultDto.setDtoList( new ArrayList<MaterialsDto>( list ) );
        }
        materialsPageResultDto.setTotalPage( pageResultDto.getTotalPage() );
        materialsPageResultDto.setPage( pageResultDto.getPage() );
        materialsPageResultDto.setSize( pageResultDto.getSize() );
        materialsPageResultDto.setStartPage( pageResultDto.getStartPage() );
        materialsPageResultDto.setEndPage( pageResultDto.getEndPage() );
        materialsPageResultDto.setHasPrev( pageResultDto.isHasPrev() );
        materialsPageResultDto.setHasNext( pageResultDto.isHasNext() );
        List<Integer> list1 = pageResultDto.getPageList();
        if ( list1 != null ) {
            materialsPageResultDto.setPageList( new ArrayList<Integer>( list1 ) );
        }

        return materialsPageResultDto;
    }

    private String entityClassroomClassroomId(MaterialsEntity materialsEntity) {
        if ( materialsEntity == null ) {
            return null;
        }
        ClassroomEntity classroom = materialsEntity.getClassroom();
        if ( classroom == null ) {
            return null;
        }
        String classroomId = classroom.getClassroomId();
        if ( classroomId == null ) {
            return null;
        }
        return classroomId;
    }

    private String entityStudentUserId(MaterialsEntity materialsEntity) {
        if ( materialsEntity == null ) {
            return null;
        }
        StudentEntity student = materialsEntity.getStudent();
        if ( student == null ) {
            return null;
        }
        String userId = student.getUserId();
        if ( userId == null ) {
            return null;
        }
        return userId;
    }
}
