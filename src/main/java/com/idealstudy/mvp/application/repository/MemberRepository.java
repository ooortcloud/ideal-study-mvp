package com.idealstudy.mvp.application.repository;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.member.*;
import com.idealstudy.mvp.enums.member.Gender;
import com.idealstudy.mvp.enums.member.Grade;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.enums.member.SchoolRegister;

import java.util.UUID;


public interface MemberRepository {

    TeacherDto createTeacher(String encodedPassword, String email, Integer fromSocial);
    ParentsDto createParents(String encodedPassword, String email, Integer fromSocial);
    StudentDto createStudent(String encodedPassword, String email, Integer fromSocial);

    MemberDto findById(String id);
    TeacherDto findTeacherById(String id);
    ParentsDto findParentsById(String id);
    StudentDto findStudentById(String id);
    // AdminDto findAdminById(String id);

    MemberDto findByEmail(String email);

    MemberPageResultDto findMembers(int page);


    MemberDto update(String userId, String phoneAddress, String introduction, String profile);
    TeacherDto update(String teacherId, String univ, SchoolRegister status, String subject);
    // ParentsDto update(ParentsDto dto);
    StudentDto update(String studentId, String school, Grade grade);

    /**
     * 회원 탈퇴 시 DB에 완전히 제거하는 것이 아니라 상태값을 변경하는 것으로 처리한다.
     * @param id
     */
    boolean deleteById(String id);
}
