package com.idealstudy.mvp.infrastructure.impl.member;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.member.*;
import com.idealstudy.mvp.enums.error.MemberErrorMsg;
import com.idealstudy.mvp.enums.member.*;
import com.idealstudy.mvp.application.repository.MemberRepository;
import com.idealstudy.mvp.error.CustomException;
import com.idealstudy.mvp.helper.WebUrlHelper;
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

    private final MemberJpaRepository memberJpaRepository;

    private final TeacherJpaRepository teacherJpaRepository;

    private final StudentJpaRepository studentJpaRepository;

    private final  ParentsJpaRepository parentsJpaRepository;

    private final  MemberMapper memberMapper;

    private final WebUrlHelper webUrlBuilder;

    private static final int SIZE = 10;

    @Autowired
    public MemberRepositoryImpl(MemberJpaRepository memberJpaRepository, TeacherJpaRepository teacherJpaRepository,
                                StudentJpaRepository studentJpaRepository, ParentsJpaRepository parentsJpaRepository,
                                MemberMapper memberMapper, WebUrlHelper webUrlBuilder) {
        this.memberJpaRepository = memberJpaRepository;
        this.teacherJpaRepository = teacherJpaRepository;
        this.studentJpaRepository = studentJpaRepository;
        this.parentsJpaRepository = parentsJpaRepository;
        this.memberMapper = memberMapper;
        this.webUrlBuilder = webUrlBuilder;
    }

    @Override
    public TeacherDto createTeacher(String userId, String encodedPassword, String email, Integer fromSocial,
                                    String defaultImage) {

        TeacherEntity entity = TeacherEntity.builder()
                .userId(userId)
                .password(encodedPassword)
                .email(email)
                .fromSocial(fromSocial)
                .role(Role.ROLE_TEACHER)
                .init(1)
                .profileUri(defaultImage)
                .build();

        TeacherDto teacherDto = MemberMapper.INSTANCE.entityToDto(teacherJpaRepository.save(entity));
        setProfileForCdn(teacherDto);

        return teacherDto;
    }

    @Override
    public ParentsDto createParents(String userId, String encodedPassword, String email, Integer fromSocial,
                                    String defaultImage) {

        String uuid = UUID.randomUUID().toString();

        ParentsEntity entity = ParentsEntity.builder()
                .userId(uuid)
                .password(encodedPassword)
                .email(email)
                .fromSocial(fromSocial)
                .role(Role.ROLE_PARENTS)
                .init(1)
                .profileUri(defaultImage)
                .build();

        ParentsDto parentsDto = MemberMapper.INSTANCE.entityToDto(parentsJpaRepository.save(entity));
        setProfileForCdn(parentsDto);

        return parentsDto;
    }

    @Override
    public StudentDto createStudent(String userId, String encodedPassword, String email, Integer fromSocial,
                                    String defaultImage) {

        StudentEntity entity = StudentEntity.builder()
                .userId(userId)
                .password(encodedPassword)
                .email(email)
                .fromSocial(fromSocial)
                .role(Role.ROLE_STUDENT)
                .init(1)
                .profileUri(defaultImage)
                .build();

        StudentDto studentDto = MemberMapper.INSTANCE.entityToDto(studentJpaRepository.save(entity));
        setProfileForCdn(studentDto);

        return studentDto;
    }

    @Override
    public MemberDto findById(String id) {

        Optional<MemberEntity> result = memberJpaRepository.findById(id);

        if(result.isEmpty())
            throw new CustomException(MemberErrorMsg.NOT_REGISTERED_MEMBER);

        MemberDto dto = memberMapper.entityToDto(result.get());
        setProfileForCdn(dto);

        return dto;
    }

    @Override
    public TeacherDto findTeacherById(String id) {

        Optional<TeacherEntity> result = teacherJpaRepository.findById(id);

        if(result.isEmpty())
            throw new CustomException(MemberErrorMsg.NOT_REGISTERED_MEMBER);

        TeacherDto teacherDto = memberMapper.entityToDto(result.get());
        setProfileForCdn(teacherDto);

        return teacherDto;
    }

    @Override
    public ParentsDto findParentsById(String id) {

        Optional<ParentsEntity> result = parentsJpaRepository.findById(id);

        if(result.isEmpty())
            throw new CustomException(MemberErrorMsg.NOT_REGISTERED_MEMBER);

        ParentsDto parentsDto = memberMapper.entityToDto(result.get());
        setProfileForCdn(parentsDto);

        return parentsDto;
    }

    @Override
    public StudentDto findStudentById(String id) {

        Optional<StudentEntity> result = studentJpaRepository.findById(id);

        if(result.isEmpty())
            throw new CustomException(MemberErrorMsg.NOT_REGISTERED_MEMBER);

        StudentDto studentDto = memberMapper.entityToDto(result.get());
        setProfileForCdn(studentDto);

        return studentDto;
    }


    @Override
    public MemberDto findByEmail(String email) {

        MemberEntity result = memberJpaRepository.findByEmail(email);

        if(result == null)
            throw new CustomException(MemberErrorMsg.NOT_REGISTERED_MEMBER);

        MemberDto memberDto = memberMapper.entityToDto(result);
        setProfileForCdn(memberDto);

        return memberDto;
    }

    @Override
    public MemberPageResultDto findMembers(int page) {

        PageRequestDto requestDto = new PageRequestDto(page, SIZE);

        Pageable pageable = requestDto.getPageable(Sort.by("regDate").descending());

        Page<MemberEntity> result = memberJpaRepository.findAll(pageable);

        Function<MemberEntity, MemberListDto> fn = (entity -> {
            MemberListDto memberListDto = memberMapper.entityToListDto(entity);
            setProfileForCdn(memberListDto);

            return memberListDto;
        });

        /// error
        MemberPageResultDto returnDto = memberMapper.toApplicationPageResult(
                new PageResultDto<MemberListDto, MemberEntity>(result, fn));

        log.info(returnDto);

        return returnDto;
    }

    /*
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

     */

    @Override
    public TeacherDto update(String teacherId, String name, String phoneAddress, Gender gender,
                             String univ, SchoolRegister status, String subject) {

        TeacherEntity entity = teacherJpaRepository.findById(teacherId).orElseThrow();

        // 초기 로그인 후처리
        if(entity.getInit() == 0)
            entity.setInit(1);

        if(name != null)
            entity.setName(name);

        if(phoneAddress != null)
            entity.setPhoneAddress(phoneAddress);

        if(gender != null)
            entity.setSex(gender);

        if(univ != null)
            entity.setUniv(univ);

        if(status != null)
            entity.setStatus(status);

        if(subject != null)
            entity.setSubject(subject);

        TeacherDto teacherDto = memberMapper.entityToDto(teacherJpaRepository.save(entity));
        setProfileForCdn(teacherDto);

        return teacherDto;
    }

    @Override
    public StudentDto update(String studentId, String name, String phoneAddress, Gender gender,
                             String school, Grade grade) {

        StudentEntity entity = studentJpaRepository.findById(studentId).orElseThrow();

        // 초기 로그인 후처리
        if(entity.getInit() == 0)
            entity.setInit(1);

        if(name != null)
            entity.setName(name);

        if(phoneAddress != null)
            entity.setPhoneAddress(phoneAddress);

        if(gender != null)
            entity.setSex(gender);

        if(school != null)
            entity.setSchool(school);

        if(grade != null)
            entity.setGrade(grade);

        StudentDto studentDto = memberMapper.entityToDto(studentJpaRepository.save(entity));
        setProfileForCdn(studentDto);

        return studentDto;
    }

    @Override
    public MemberDto updateIntroduction(String userId, String introduction) {

        MemberEntity entity = memberJpaRepository.findById(userId).orElseThrow();

        entity.setIntroduction(introduction);

        MemberDto memberDto = MemberMapper.INSTANCE.entityToDto(memberJpaRepository.save(entity));
        setProfileForCdn(memberDto);

        return memberDto;
    }

    @Override
    public void updateProfile(String userId, String profileUri) {

        MemberEntity entity = memberJpaRepository.findById(userId).orElseThrow();

        entity.setProfileUri(profileUri);

        MemberEntity savedEntity = memberJpaRepository.save(entity);
        // log.info("DB에 저장된 profileUri: " + savedEntity.getProfileUri());
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

    private void setProfileForCdn(MemberDto dto) {
        dto.setProfileUri(webUrlBuilder.makeCdnLink(dto.getProfileUri()));
    }

    private void setProfileForCdn(MemberListDto dto) {
        dto.setProfileUri(webUrlBuilder.makeCdnLink(dto.getProfileUri()));
    }
}

