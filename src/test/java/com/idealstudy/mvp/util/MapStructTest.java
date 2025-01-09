package com.idealstudy.mvp.util;

import com.idealstudy.mvp.application.dto.member.ParentsDto;
import com.idealstudy.mvp.application.dto.member.StudentDto;
import com.idealstudy.mvp.application.dto.member.TeacherDto;
import com.idealstudy.mvp.enums.member.Gender;
import com.idealstudy.mvp.enums.member.Grade;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.enums.member.SchoolRegister;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.ParentsEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.StudentEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.TeacherEntity;
import com.idealstudy.mvp.mapstruct.MemberMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class MapStructTest {

    private final MemberMapper memberMapper;

    private final String USER_ID = UUID.randomUUID().toString();

    @Autowired
    public MapStructTest(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Test
    public void dtoToEntity_Teacher() {

        Role role = Role.ROLE_TEACHER;
        String univ = "실험대학교";
        SchoolRegister schoolRegister = SchoolRegister.GRADUATION;
        String subject = "실험 과목";

        TeacherDto dto = TeacherDto.builder()
                .userId(USER_ID)
                .password("testpassword")
                .email("test@test.com")
                .fromSocial(0)
                .role(role)
                .sex(Gender.MALE)
                .univ(univ)
                .status(schoolRegister)
                .subject(subject)
                .build();

        TeacherEntity entity = memberMapper.dtoToEntity(dto);

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getUserId()).isEqualTo(USER_ID);
        Assertions.assertThat(entity.getRole()).isEqualTo(Role.ROLE_TEACHER);
        Assertions.assertThat(entity.getUniv()).isEqualTo(univ);
        Assertions.assertThat(entity.getStatus()).isEqualTo(schoolRegister);
        Assertions.assertThat(entity.getSubject()).isEqualTo(subject);

    }

    @Test
    public void dtoToEntity_Parents() {

        Role role = Role.ROLE_PARENTS;

        ParentsDto dto = ParentsDto.builder()
                .userId(USER_ID)
                .password("testpassword")
                .email("test@test.com")
                .fromSocial(0)
                .role(role)
                .sex(Gender.MALE)
                .build();

        ParentsEntity entity = memberMapper.dtoToEntity(dto);

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getUserId()).isEqualTo(USER_ID);
        Assertions.assertThat(entity.getRole()).isEqualTo(role);
    }

    @Test
    public void dtoToEntity_Student() {

        Grade grade = Grade.H2;
        String school = "실험고등학교";
        Role role = Role.ROLE_STUDENT;

        StudentDto dto = StudentDto.builder()
                .userId(USER_ID)
                .password("testpassword")
                .email("test@test.com")
                .fromSocial(0)
                .role(role)
                .sex(Gender.MALE)
                .grade(grade)
                .school(school)
                .build();

        StudentEntity entity = memberMapper.dtoToEntity(dto);

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getUserId()).isEqualTo(USER_ID);
        Assertions.assertThat(entity.getRole()).isEqualTo(role);
        Assertions.assertThat(entity.getGrade()).isEqualTo(grade);
        Assertions.assertThat(entity.getSchool()).isEqualTo(school);
    }

    @Test
    public void entityToDto_Teacher() {

        Role role = Role.ROLE_TEACHER;
        String univ = "실험대학교";
        SchoolRegister schoolRegister = SchoolRegister.GRADUATION;
        String subject = "실험 과목";

        TeacherEntity entity = TeacherEntity.builder()
                .userId(USER_ID)
                .password("testpassword")
                .email("test@test.com")
                .fromSocial(0)
                .role(role)
                .sex(Gender.MALE)
                .univ(univ)
                .status(schoolRegister)
                .subject(subject)
                .build();

        TeacherDto dto = memberMapper.entityToDto(entity);

        Assertions.assertThat(dto).isNotNull();
        Assertions.assertThat(dto.getUserId()).isEqualTo(USER_ID);
        Assertions.assertThat(dto.getRole()).isEqualTo(Role.ROLE_TEACHER);
        Assertions.assertThat(dto.getUniv()).isEqualTo(univ);
        Assertions.assertThat(dto.getStatus()).isEqualTo(schoolRegister);
        Assertions.assertThat(dto.getSubject()).isEqualTo(subject);
    }

    @Test
    public void entityToDto_Parents() {

        Role role = Role.ROLE_PARENTS;

        ParentsEntity entity = ParentsEntity.builder()
                .userId(USER_ID)
                .password("testpassword")
                .email("test@test.com")
                .fromSocial(0)
                .role(role)
                .sex(Gender.MALE)
                .build();

        ParentsDto dto = memberMapper.entityToDto(entity);

        Assertions.assertThat(dto).isNotNull();
        Assertions.assertThat(dto.getUserId()).isEqualTo(USER_ID);
        Assertions.assertThat(dto.getRole()).isEqualTo(role);
    }

    @Test
    public void entityToDto_Student() {

        Grade grade = Grade.H2;
        String school = "실험고등학교";
        Role role = Role.ROLE_STUDENT;

        StudentEntity entity = StudentEntity.builder()
                .userId(USER_ID)
                .password("testpassword")
                .email("test@test.com")
                .fromSocial(0)
                .role(role)
                .sex(Gender.MALE)
                .grade(grade)
                .school(school)
                .build();

        StudentDto dto = memberMapper.entityToDto(entity);

        Assertions.assertThat(dto).isNotNull();
        Assertions.assertThat(dto.getUserId()).isEqualTo(USER_ID);
        Assertions.assertThat(dto.getRole()).isEqualTo(role);
        Assertions.assertThat(dto.getGrade()).isEqualTo(grade);
        Assertions.assertThat(dto.getSchool()).isEqualTo(school);
    }
}
