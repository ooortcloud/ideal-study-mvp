package com.idealstudy.mvp.infrastructure.impl.member;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.member.*;
import com.idealstudy.mvp.enums.member.*;
import com.idealstudy.mvp.application.repository.MemberRepository;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.MemberEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.ParentsEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.StudentEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.TeacherEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.member.*;
import com.idealstudy.mvp.mapstruct.MemberMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Repository
@Log4j2
public class MemberRepositoryImpl implements MemberRepository {

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Autowired
    private TeacherJpaRepository teacherJpaRepository;

    @Autowired
    private StudentJpaRepository studentJpaRepository;

    @Autowired
    private ParentsJpaRepository parentsJpaRepository;

    @Autowired
    private MemberMapper memberMapper;

    private static final int SIZE = 10;

    @Override
    public TeacherDto createTeacher(String userId, String encodedPassword, String email, Integer fromSocial) {

        String uuid = UUID.randomUUID().toString();

        TeacherEntity entity = TeacherEntity.builder()
                .userId(uuid)
                .password(encodedPassword)
                .email(email)
                .fromSocial(fromSocial)
                .role(Role.ROLE_TEACHER)
                .init(1)
                .build();

        return MemberMapper.INSTANCE.entityToDto(teacherJpaRepository.save(entity));
    }

    @Override
    public ParentsDto createParents(String userId, String encodedPassword, String email, Integer fromSocial) {

        String uuid = UUID.randomUUID().toString();

        ParentsEntity entity = ParentsEntity.builder()
                .userId(uuid)
                .password(encodedPassword)
                .email(email)
                .fromSocial(fromSocial)
                .role(Role.ROLE_PARENTS)
                .init(1)
                .build();

        return MemberMapper.INSTANCE.entityToDto(parentsJpaRepository.save(entity));
    }

    @Override
    public StudentDto createStudent(String userId, String encodedPassword, String email, Integer fromSocial) {

        String uuid = UUID.randomUUID().toString();

        StudentEntity entity = StudentEntity.builder()
                .userId(uuid)
                .password(encodedPassword)
                .email(email)
                .fromSocial(fromSocial)
                .role(Role.ROLE_STUDENT)
                .init(1)
                .build();

        return MemberMapper.INSTANCE.entityToDto(studentJpaRepository.save(entity));
    }

    /**
     * MEMBER table에 저장된 데이터에 한해서만 조회하는 메소드
     * @param id
     * @return
     */
    @Override
    public MemberDto findById(String id) {

        Optional<MemberEntity> result = memberJpaRepository.findById(id);

        if(result.isEmpty())
            throw new IllegalArgumentException(MemberError.NOT_REGISTERED_MEMBER.getMsg());

        return memberMapper.entityToDto(result.get());
    }

    @Override
    public TeacherDto findTeacherById(String id) {

        Optional<TeacherEntity> result = teacherJpaRepository.findById(id);

        if(result.isEmpty())
            throw new IllegalArgumentException(MemberError.NOT_REGISTERED_MEMBER.getMsg());

        return memberMapper.entityToDto(result.get());
    }

    @Override
    public ParentsDto findParentsById(String id) {

        Optional<ParentsEntity> result = parentsJpaRepository.findById(id);

        if(result.isEmpty())
            throw new IllegalArgumentException(MemberError.NOT_REGISTERED_MEMBER.getMsg());

        return memberMapper.entityToDto(result.get());
    }

    @Override
    public StudentDto findStudentById(String id) {

        Optional<StudentEntity> result = studentJpaRepository.findById(id);

        if(result.isEmpty())
            throw new NullPointerException(MemberError.NOT_REGISTERED_MEMBER.getMsg());

        return memberMapper.entityToDto(result.get());
    }


    @Override
    public MemberDto findByEmail(String email) {

        MemberEntity result = memberJpaRepository.findByEmail(email);

        if(result == null)
            throw new NullPointerException(MemberError.NOT_REGISTERED_MEMBER.getMsg());

        return memberMapper.entityToDto(result);
    }

    @Override
    public MemberPageResultDto findMembers(int page) {

        PageRequestDto requestDto = new PageRequestDto(page, SIZE);

        Pageable pageable = requestDto.getPageable(Sort.by("regDate").descending());

        Page<MemberEntity> result = memberJpaRepository.findAll(pageable);

        Function<MemberEntity, MemberListDto> fn = (entity -> memberMapper.entityToListDto(entity));

        return memberMapper.toApplicationPageResult(new PageResultDto<>(result, fn));
    }

    @Override
    public MemberDto update(String userId, String phoneAddress, String profile) {

        MemberEntity entity = memberJpaRepository.findById(userId).orElseThrow();

        if(phoneAddress != null)
            entity.setPhoneAddress(phoneAddress);

        if(profile != null)
            entity.setProfile(null);

        MemberEntity result = memberJpaRepository.save(entity);

        return memberMapper.entityToDto(result);
    }

    @Override
    public TeacherDto update(String teacherId, String univ, SchoolRegister status, String subject) {

        TeacherEntity entity = teacherJpaRepository.findById(teacherId).orElseThrow();

        if(univ != null)
            entity.setUniv(univ);

        if(status != null)
            entity.setStatus(status);

        if(subject != null)
            entity.setSubject(subject);

        return memberMapper.entityToDto(teacherJpaRepository.save(entity));
    }

    @Override
    public StudentDto update(String studentId, String school, Grade grade) {

        StudentEntity entity = studentJpaRepository.findById(studentId).orElseThrow();

        if(school != null)
            entity.setSchool(school);

        if(grade != null)
            entity.setGrade(grade);

        return memberMapper.entityToDto(studentJpaRepository.save(entity));
    }

    @Override
    public MemberDto updateIntroduction(String userId, String introduction) {

        MemberEntity entity = memberJpaRepository.findById(userId).orElseThrow();

        entity.setIntroduction(introduction);

        return MemberMapper.INSTANCE.entityToDto(memberJpaRepository.save(entity));
    }

    /**
     * 아직 기능이 구체화되지 않았습니다.
     * @param dto
     * @return
     */
    @Deprecated
    public ParentsDto update(ParentsDto dto) {
        ParentsEntity entity = parentsJpaRepository.findById(dto.getUserId()).orElseThrow();

        memberMapper.updateEntityFromDto(dto, entity);

        return memberMapper.entityToDto(parentsJpaRepository.save(entity));
    }

    @Override
    public boolean deleteById(String id) {

        try {
            MemberEntity entity = memberJpaRepository.findById(id).orElseThrow();
            entity.setDeleted(1);
            memberJpaRepository.save(entity);
        } catch (Exception e) {
            log.info(e + " : " + e.getMessage());
            return false;
        }

        return true;
    }

}

