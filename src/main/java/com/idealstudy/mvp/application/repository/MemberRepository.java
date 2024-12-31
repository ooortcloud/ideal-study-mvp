package com.idealstudy.mvp.application.repository;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.member.*;
import com.idealstudy.mvp.enums.member.Gender;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.enums.member.SchoolRegister;

import java.util.UUID;


public interface MemberRepository {

    default void createDummyTeacher(String userId) {
        String univ = "한국대학교";
        SchoolRegister schoolRegister = SchoolRegister.GRADUATION;
        String subject = "수학";

        TeacherDto dto = TeacherDto.builder()
                .userId(userId)
                .password("abcd1234")
                .phoneAddress("010-1234-1234")
                .email("testteacher@gmail.com")
                .role(Role.ROLE_TEACHER)
                .sex(Gender.MALE)
                .referralId(UUID.randomUUID().toString())
                .fromSocial(0)
                .univ(univ)
                .status(schoolRegister)
                .subject(subject)
                .build();

        create(dto);
    }

    void create(TeacherDto dto);
    void create(ParentsDto dto);
    void create(StudentDto dto);
    // void create(AdminDto dto);

    MemberDto findById(String id);
    TeacherDto findTeacherById(String id);
    ParentsDto findParentsById(String id);
    StudentDto findStudentById(String id);
    // AdminDto findAdminById(String id);

    MemberDto findByEmail(String email);

    MemberPageResultDto findMembers(int page);


    MemberDto update(String userId, String phoneAddress, String introduction, String profile);
    TeacherDto update(TeacherDto dto);
    ParentsDto update(ParentsDto dto);
    StudentDto update(StudentDto dto);
    // AdminDto update(AdminDto dto);

    /**
     * 회원 탈퇴 시 DB에 완전히 제거하는 것이 아니라 상태값을 변경하는 것으로 처리한다.
     * @param id
     */
    boolean deleteById(String id);
}
