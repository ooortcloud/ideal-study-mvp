package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.member.AdminDto;
import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.application.dto.member.MemberListDto;
import com.idealstudy.mvp.application.dto.member.MemberPageResultDto;
import com.idealstudy.mvp.application.dto.member.ParentsDto;
import com.idealstudy.mvp.application.dto.member.StudentDto;
import com.idealstudy.mvp.application.dto.member.TeacherDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.AdminEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.MemberEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.ParentsEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.StudentEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.TeacherEntity;
import com.idealstudy.mvp.presentation.dto.member.MemberResponseDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-05T23:53:02+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public MemberDto entityToDto(MemberEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MemberDto.MemberDtoBuilder<?, ?> memberDto = MemberDto.builder();

        memberDto.userId( entity.getUserId() );
        memberDto.password( entity.getPassword() );
        memberDto.name( entity.getName() );
        memberDto.phoneAddress( entity.getPhoneAddress() );
        memberDto.email( entity.getEmail() );
        memberDto.sex( entity.getSex() );
        memberDto.referralId( entity.getReferralId() );
        memberDto.level( entity.getLevel() );
        memberDto.role( entity.getRole() );
        memberDto.introduction( entity.getIntroduction() );
        byte[] profile = entity.getProfile();
        if ( profile != null ) {
            memberDto.profile( Arrays.copyOf( profile, profile.length ) );
        }
        memberDto.fromSocial( entity.getFromSocial() );
        memberDto.init( entity.getInit() );
        memberDto.deleted( entity.getDeleted() );

        return memberDto.build();
    }

    @Override
    public MemberListDto entityToListDto(MemberEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MemberListDto.MemberListDtoBuilder memberListDto = MemberListDto.builder();

        memberListDto.userId( entity.getUserId() );
        memberListDto.name( entity.getName() );
        byte[] profile = entity.getProfile();
        if ( profile != null ) {
            memberListDto.profile( Arrays.copyOf( profile, profile.length ) );
        }

        return memberListDto.build();
    }

    @Override
    public MemberResponseDto toResponseDto(MemberDto dto) {
        if ( dto == null ) {
            return null;
        }

        MemberResponseDto.MemberResponseDtoBuilder memberResponseDto = MemberResponseDto.builder();

        memberResponseDto.userId( dto.getUserId() );
        memberResponseDto.name( dto.getName() );
        memberResponseDto.phoneAddress( dto.getPhoneAddress() );
        memberResponseDto.email( dto.getEmail() );
        memberResponseDto.sex( dto.getSex() );
        memberResponseDto.level( dto.getLevel() );
        memberResponseDto.role( dto.getRole() );
        memberResponseDto.introduction( dto.getIntroduction() );
        byte[] profile = dto.getProfile();
        if ( profile != null ) {
            memberResponseDto.profile( Arrays.copyOf( profile, profile.length ) );
        }

        return memberResponseDto.build();
    }

    @Override
    public TeacherEntity dtoToEntity(TeacherDto dto) {
        if ( dto == null ) {
            return null;
        }

        TeacherEntity.TeacherEntityBuilder<?, ?> teacherEntity = TeacherEntity.builder();

        teacherEntity.userId( dto.getUserId() );
        teacherEntity.password( dto.getPassword() );
        teacherEntity.name( dto.getName() );
        teacherEntity.phoneAddress( dto.getPhoneAddress() );
        teacherEntity.email( dto.getEmail() );
        teacherEntity.sex( dto.getSex() );
        teacherEntity.referralId( dto.getReferralId() );
        teacherEntity.level( dto.getLevel() );
        teacherEntity.role( dto.getRole() );
        teacherEntity.introduction( dto.getIntroduction() );
        byte[] profile = dto.getProfile();
        if ( profile != null ) {
            teacherEntity.profile( Arrays.copyOf( profile, profile.length ) );
        }
        teacherEntity.fromSocial( dto.getFromSocial() );
        teacherEntity.init( dto.getInit() );
        teacherEntity.deleted( dto.getDeleted() );
        teacherEntity.univ( dto.getUniv() );
        teacherEntity.status( dto.getStatus() );
        teacherEntity.subject( dto.getSubject() );

        return teacherEntity.build();
    }

    @Override
    public ParentsEntity dtoToEntity(ParentsDto dto) {
        if ( dto == null ) {
            return null;
        }

        ParentsEntity.ParentsEntityBuilder<?, ?> parentsEntity = ParentsEntity.builder();

        parentsEntity.userId( dto.getUserId() );
        parentsEntity.password( dto.getPassword() );
        parentsEntity.name( dto.getName() );
        parentsEntity.phoneAddress( dto.getPhoneAddress() );
        parentsEntity.email( dto.getEmail() );
        parentsEntity.sex( dto.getSex() );
        parentsEntity.referralId( dto.getReferralId() );
        parentsEntity.level( dto.getLevel() );
        parentsEntity.role( dto.getRole() );
        parentsEntity.introduction( dto.getIntroduction() );
        byte[] profile = dto.getProfile();
        if ( profile != null ) {
            parentsEntity.profile( Arrays.copyOf( profile, profile.length ) );
        }
        parentsEntity.fromSocial( dto.getFromSocial() );
        parentsEntity.init( dto.getInit() );
        parentsEntity.deleted( dto.getDeleted() );

        return parentsEntity.build();
    }

    @Override
    public StudentEntity dtoToEntity(StudentDto dto) {
        if ( dto == null ) {
            return null;
        }

        StudentEntity.StudentEntityBuilder<?, ?> studentEntity = StudentEntity.builder();

        studentEntity.userId( dto.getUserId() );
        studentEntity.password( dto.getPassword() );
        studentEntity.name( dto.getName() );
        studentEntity.phoneAddress( dto.getPhoneAddress() );
        studentEntity.email( dto.getEmail() );
        studentEntity.sex( dto.getSex() );
        studentEntity.referralId( dto.getReferralId() );
        studentEntity.level( dto.getLevel() );
        studentEntity.role( dto.getRole() );
        studentEntity.introduction( dto.getIntroduction() );
        byte[] profile = dto.getProfile();
        if ( profile != null ) {
            studentEntity.profile( Arrays.copyOf( profile, profile.length ) );
        }
        studentEntity.fromSocial( dto.getFromSocial() );
        studentEntity.init( dto.getInit() );
        studentEntity.deleted( dto.getDeleted() );
        studentEntity.school( dto.getSchool() );
        studentEntity.grade( dto.getGrade() );

        return studentEntity.build();
    }

    @Override
    public AdminEntity dtoToEntity(AdminDto dto) {
        if ( dto == null ) {
            return null;
        }

        AdminEntity.AdminEntityBuilder<?, ?> adminEntity = AdminEntity.builder();

        adminEntity.userId( dto.getUserId() );
        adminEntity.password( dto.getPassword() );
        adminEntity.name( dto.getName() );
        adminEntity.phoneAddress( dto.getPhoneAddress() );
        adminEntity.email( dto.getEmail() );
        adminEntity.sex( dto.getSex() );
        adminEntity.referralId( dto.getReferralId() );
        adminEntity.level( dto.getLevel() );
        adminEntity.role( dto.getRole() );
        adminEntity.introduction( dto.getIntroduction() );
        byte[] profile = dto.getProfile();
        if ( profile != null ) {
            adminEntity.profile( Arrays.copyOf( profile, profile.length ) );
        }
        adminEntity.fromSocial( dto.getFromSocial() );
        adminEntity.init( dto.getInit() );
        adminEntity.deleted( dto.getDeleted() );

        return adminEntity.build();
    }

    @Override
    public TeacherDto entityToDto(TeacherEntity entity) {
        if ( entity == null ) {
            return null;
        }

        TeacherDto.TeacherDtoBuilder<?, ?> teacherDto = TeacherDto.builder();

        teacherDto.userId( entity.getUserId() );
        teacherDto.password( entity.getPassword() );
        teacherDto.name( entity.getName() );
        teacherDto.phoneAddress( entity.getPhoneAddress() );
        teacherDto.email( entity.getEmail() );
        teacherDto.sex( entity.getSex() );
        teacherDto.referralId( entity.getReferralId() );
        teacherDto.level( entity.getLevel() );
        teacherDto.role( entity.getRole() );
        teacherDto.introduction( entity.getIntroduction() );
        byte[] profile = entity.getProfile();
        if ( profile != null ) {
            teacherDto.profile( Arrays.copyOf( profile, profile.length ) );
        }
        teacherDto.fromSocial( entity.getFromSocial() );
        teacherDto.init( entity.getInit() );
        teacherDto.deleted( entity.getDeleted() );
        teacherDto.univ( entity.getUniv() );
        teacherDto.status( entity.getStatus() );
        teacherDto.subject( entity.getSubject() );

        return teacherDto.build();
    }

    @Override
    public ParentsDto entityToDto(ParentsEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ParentsDto.ParentsDtoBuilder<?, ?> parentsDto = ParentsDto.builder();

        parentsDto.userId( entity.getUserId() );
        parentsDto.password( entity.getPassword() );
        parentsDto.name( entity.getName() );
        parentsDto.phoneAddress( entity.getPhoneAddress() );
        parentsDto.email( entity.getEmail() );
        parentsDto.sex( entity.getSex() );
        parentsDto.referralId( entity.getReferralId() );
        parentsDto.level( entity.getLevel() );
        parentsDto.role( entity.getRole() );
        parentsDto.introduction( entity.getIntroduction() );
        byte[] profile = entity.getProfile();
        if ( profile != null ) {
            parentsDto.profile( Arrays.copyOf( profile, profile.length ) );
        }
        parentsDto.fromSocial( entity.getFromSocial() );
        parentsDto.init( entity.getInit() );
        parentsDto.deleted( entity.getDeleted() );

        return parentsDto.build();
    }

    @Override
    public StudentDto entityToDto(StudentEntity entity) {
        if ( entity == null ) {
            return null;
        }

        StudentDto.StudentDtoBuilder<?, ?> studentDto = StudentDto.builder();

        studentDto.userId( entity.getUserId() );
        studentDto.password( entity.getPassword() );
        studentDto.name( entity.getName() );
        studentDto.phoneAddress( entity.getPhoneAddress() );
        studentDto.email( entity.getEmail() );
        studentDto.sex( entity.getSex() );
        studentDto.referralId( entity.getReferralId() );
        studentDto.level( entity.getLevel() );
        studentDto.role( entity.getRole() );
        studentDto.introduction( entity.getIntroduction() );
        byte[] profile = entity.getProfile();
        if ( profile != null ) {
            studentDto.profile( Arrays.copyOf( profile, profile.length ) );
        }
        studentDto.fromSocial( entity.getFromSocial() );
        studentDto.init( entity.getInit() );
        studentDto.deleted( entity.getDeleted() );
        studentDto.school( entity.getSchool() );
        studentDto.grade( entity.getGrade() );

        return studentDto.build();
    }

    @Override
    public AdminDto entityToDto(AdminEntity entity) {
        if ( entity == null ) {
            return null;
        }

        AdminDto.AdminDtoBuilder<?, ?> adminDto = AdminDto.builder();

        adminDto.userId( entity.getUserId() );
        adminDto.password( entity.getPassword() );
        adminDto.name( entity.getName() );
        adminDto.phoneAddress( entity.getPhoneAddress() );
        adminDto.email( entity.getEmail() );
        adminDto.sex( entity.getSex() );
        adminDto.referralId( entity.getReferralId() );
        adminDto.level( entity.getLevel() );
        adminDto.role( entity.getRole() );
        adminDto.introduction( entity.getIntroduction() );
        byte[] profile = entity.getProfile();
        if ( profile != null ) {
            adminDto.profile( Arrays.copyOf( profile, profile.length ) );
        }
        adminDto.fromSocial( entity.getFromSocial() );
        adminDto.init( entity.getInit() );
        adminDto.deleted( entity.getDeleted() );

        return adminDto.build();
    }

    @Override
    public void updateEntityFromDto(MemberDto dto, MemberEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getPassword() != null ) {
            entity.setPassword( dto.getPassword() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getPhoneAddress() != null ) {
            entity.setPhoneAddress( dto.getPhoneAddress() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getSex() != null ) {
            entity.setSex( dto.getSex() );
        }
        if ( dto.getReferralId() != null ) {
            entity.setReferralId( dto.getReferralId() );
        }
        if ( dto.getLevel() != null ) {
            entity.setLevel( dto.getLevel() );
        }
        if ( dto.getRole() != null ) {
            entity.setRole( dto.getRole() );
        }
        if ( dto.getIntroduction() != null ) {
            entity.setIntroduction( dto.getIntroduction() );
        }
        byte[] profile = dto.getProfile();
        if ( profile != null ) {
            entity.setProfile( Arrays.copyOf( profile, profile.length ) );
        }
        entity.setFromSocial( dto.getFromSocial() );
        entity.setInit( dto.getInit() );
        entity.setDeleted( dto.getDeleted() );
    }

    @Override
    public void updateEntityFromDto(TeacherDto dto, TeacherEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getPassword() != null ) {
            entity.setPassword( dto.getPassword() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getPhoneAddress() != null ) {
            entity.setPhoneAddress( dto.getPhoneAddress() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getSex() != null ) {
            entity.setSex( dto.getSex() );
        }
        if ( dto.getReferralId() != null ) {
            entity.setReferralId( dto.getReferralId() );
        }
        if ( dto.getLevel() != null ) {
            entity.setLevel( dto.getLevel() );
        }
        if ( dto.getRole() != null ) {
            entity.setRole( dto.getRole() );
        }
        if ( dto.getIntroduction() != null ) {
            entity.setIntroduction( dto.getIntroduction() );
        }
        byte[] profile = dto.getProfile();
        if ( profile != null ) {
            entity.setProfile( Arrays.copyOf( profile, profile.length ) );
        }
        entity.setFromSocial( dto.getFromSocial() );
        entity.setInit( dto.getInit() );
        entity.setDeleted( dto.getDeleted() );
        if ( dto.getUniv() != null ) {
            entity.setUniv( dto.getUniv() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( dto.getStatus() );
        }
        if ( dto.getSubject() != null ) {
            entity.setSubject( dto.getSubject() );
        }
    }

    @Override
    public void updateEntityFromDto(ParentsDto dto, ParentsEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getPassword() != null ) {
            entity.setPassword( dto.getPassword() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getPhoneAddress() != null ) {
            entity.setPhoneAddress( dto.getPhoneAddress() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getSex() != null ) {
            entity.setSex( dto.getSex() );
        }
        if ( dto.getReferralId() != null ) {
            entity.setReferralId( dto.getReferralId() );
        }
        if ( dto.getLevel() != null ) {
            entity.setLevel( dto.getLevel() );
        }
        if ( dto.getRole() != null ) {
            entity.setRole( dto.getRole() );
        }
        if ( dto.getIntroduction() != null ) {
            entity.setIntroduction( dto.getIntroduction() );
        }
        byte[] profile = dto.getProfile();
        if ( profile != null ) {
            entity.setProfile( Arrays.copyOf( profile, profile.length ) );
        }
        entity.setFromSocial( dto.getFromSocial() );
        entity.setInit( dto.getInit() );
        entity.setDeleted( dto.getDeleted() );
    }

    @Override
    public void updateEntityFromDto(StudentDto dto, StudentEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getPassword() != null ) {
            entity.setPassword( dto.getPassword() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getPhoneAddress() != null ) {
            entity.setPhoneAddress( dto.getPhoneAddress() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getSex() != null ) {
            entity.setSex( dto.getSex() );
        }
        if ( dto.getReferralId() != null ) {
            entity.setReferralId( dto.getReferralId() );
        }
        if ( dto.getLevel() != null ) {
            entity.setLevel( dto.getLevel() );
        }
        if ( dto.getRole() != null ) {
            entity.setRole( dto.getRole() );
        }
        if ( dto.getIntroduction() != null ) {
            entity.setIntroduction( dto.getIntroduction() );
        }
        byte[] profile = dto.getProfile();
        if ( profile != null ) {
            entity.setProfile( Arrays.copyOf( profile, profile.length ) );
        }
        entity.setFromSocial( dto.getFromSocial() );
        entity.setInit( dto.getInit() );
        entity.setDeleted( dto.getDeleted() );
        if ( dto.getSchool() != null ) {
            entity.setSchool( dto.getSchool() );
        }
        if ( dto.getGrade() != null ) {
            entity.setGrade( dto.getGrade() );
        }
    }

    @Override
    public MemberPageResultDto toApplicationPageResult(PageResultDto<MemberListDto, MemberEntity> pageResultDto) {
        if ( pageResultDto == null ) {
            return null;
        }

        MemberPageResultDto memberPageResultDto = new MemberPageResultDto();

        List<MemberListDto> list = pageResultDto.getDtoList();
        if ( list != null ) {
            memberPageResultDto.setDtoList( new ArrayList<MemberListDto>( list ) );
        }
        memberPageResultDto.setTotalPage( pageResultDto.getTotalPage() );
        memberPageResultDto.setPage( pageResultDto.getPage() );
        memberPageResultDto.setSize( pageResultDto.getSize() );
        memberPageResultDto.setStartPage( pageResultDto.getStartPage() );
        memberPageResultDto.setEndPage( pageResultDto.getEndPage() );
        memberPageResultDto.setHasPrev( pageResultDto.isHasPrev() );
        memberPageResultDto.setHasNext( pageResultDto.isHasNext() );
        List<Integer> list1 = pageResultDto.getPageList();
        if ( list1 != null ) {
            memberPageResultDto.setPageList( new ArrayList<Integer>( list1 ) );
        }

        return memberPageResultDto;
    }
}
