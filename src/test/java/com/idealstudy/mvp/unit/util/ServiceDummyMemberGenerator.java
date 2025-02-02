package com.idealstudy.mvp.unit.util;

import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.application.dto.member.ParentsDto;
import com.idealstudy.mvp.application.dto.member.StudentDto;
import com.idealstudy.mvp.application.dto.member.TeacherDto;
import com.idealstudy.mvp.enums.member.Gender;
import com.idealstudy.mvp.enums.member.Grade;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.enums.member.SchoolRegister;

import java.util.UUID;

public class ServiceDummyMemberGenerator {


    public static StudentDto createDummyStudentDto() {
        String userId = UUID.randomUUID().toString();
        String password = "1234";
        String name = "홍길동";
        String phoneAddress = "010-1234-1234";
        String email = "teststudent@gmail.com";
        Gender sex = Gender.MALE;
        Integer level = 1;
        Role role = Role.ROLE_STUDENT;
        String introduction = "안녕하세요";

        String school = "강남고등학교";
        Grade grade = Grade.H2;

        return StudentDto.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .phoneAddress(phoneAddress)
                .email(email)
                .sex(sex)
                .level(level)
                .role(role)
                .introduction(introduction)
                .fromSocial(0)
                .init(1)
                .deleted(0)
                .school(school)
                .grade(grade)
                .build();
    }

    public static TeacherDto createDummyTeacherDto() {
        String userId = UUID.randomUUID().toString();
        String password = "1234";
        String name = "홍길동";
        String phoneAddress = "010-1234-1234";
        String email = "testteacher@gmail.com";
        Gender sex = Gender.MALE;
        Integer level = 1;
        Role role = Role.ROLE_TEACHER;
        String introduction = "안녕하세요";

        String univ = "한국대학교";
        SchoolRegister status = SchoolRegister.GRADUATION;
        String subject = "수학";

        return TeacherDto.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .phoneAddress(phoneAddress)
                .email(email)
                .sex(sex)
                .level(level)
                .role(role)
                .introduction(introduction)
                .fromSocial(0)
                .init(1)
                .deleted(0)
                .univ(univ)
                .status(status)
                .subject(subject)
                .build();
    }

    public static MemberDto createDummyMemberDto() {
        String userId = UUID.randomUUID().toString();
        String password = "1234";
        String name = "홍길동";
        String phoneAddress = "010-1234-1234";
        String email = "teststudent@gmail.com";
        Gender sex = Gender.MALE;
        Integer level = 1;
        Role role = Role.ROLE_STUDENT;
        String introduction = "안녕하세요";

        return MemberDto.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .phoneAddress(phoneAddress)
                .email(email)
                .sex(sex)
                .level(level)
                .role(role)
                .introduction(introduction)
                .fromSocial(0)
                .init(1)
                .deleted(0)
                .build();
    }

    public static ParentsDto createDummyParentsDto() {
        String userId = UUID.randomUUID().toString();
        String password = "1234";
        String name = "홍길순";
        String phoneAddress = "010-1234-1234";
        String email = "testparents@gmail.com";
        Gender sex = Gender.FEMALE;
        Integer level = 1;
        Role role = Role.ROLE_PARENTS;
        String introduction = "안녕하세요";

        return ParentsDto.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .phoneAddress(phoneAddress)
                .email(email)
                .sex(sex)
                .level(level)
                .role(role)
                .introduction(introduction)
                .fromSocial(0)
                .init(1)
                .deleted(0)
                .build();
    }
}
