package com.idealstudy.mvp.infrastructure.impl.classroom.inclass;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.EnrollmentDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.EnrollmentPageResultDto;
import com.idealstudy.mvp.enums.classroom.EnrollmentStatus;
import com.idealstudy.mvp.enums.error.DBErrorMsg;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.EnrollmentEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.StudentEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.ClassroomJpaRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.inclass.EnrollmentJpaRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.member.StudentJpaRepository;
import com.idealstudy.mvp.application.repository.inclass.EnrollmentRepository;
import com.idealstudy.mvp.mapstruct.EnrollmentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Function;

@Repository
@Slf4j
@RequiredArgsConstructor
public class EnrollmentRepositoryImpl implements EnrollmentRepository {

    @Autowired
    private final EnrollmentJpaRepository enrollmentJpaRepository;

    @Autowired
    private final ClassroomJpaRepository classroomJpaRepository;

    @Autowired
    private final StudentJpaRepository studentJpaRepository;

    private static final int SIZE = 10;

    @Override
    public EnrollmentDto enroll(String classroomId, String applicantId, String studentId, String curScore,
                                String targetScore, String request, String determination) {

        ClassroomEntity classroom = classroomJpaRepository.findById(classroomId).orElseThrow();

        StudentEntity student = studentJpaRepository.findById(studentId).orElseThrow();

        EnrollmentEntity entity = EnrollmentEntity.builder()
                .classroom(classroom)
                .createdBy(applicantId)
                .student(student)
                .curScore(curScore)
                .targetScore(targetScore)
                .request(request)
                .determination(determination)
                .build();

        return EnrollmentMapper.INSTANCE.toDto(enrollmentJpaRepository.save(entity));
    }

    @Override
    public void drop(Long id, String applicantId) {

        EnrollmentEntity entity = enrollmentJpaRepository.findById(id).orElseThrow();

        if( !entity.getCreatedBy().equals(applicantId)) {
            log.error(DBErrorMsg.DELETE_ERROR.getMsg() + ": 일치하지 않는 신청자 ID");
            throw new RuntimeException(DBErrorMsg.DELETE_ERROR.getMsg());
        }

        enrollmentJpaRepository.delete(entity);
    }

    @Override
    public EnrollmentDto check(Long id) {

        EnrollmentEntity entity = enrollmentJpaRepository.findById(id).orElseThrow();
        entity.setStatus(EnrollmentStatus.CHECKED);

        return EnrollmentMapper.INSTANCE.toDto(enrollmentJpaRepository.save(entity));
    }

    @Override
    public EnrollmentDto getInfo(Long id) {

        EnrollmentEntity entity = enrollmentJpaRepository.findById(id).orElseThrow();

        return EnrollmentMapper.INSTANCE.toDto(entity);
    }

    @Override
    public boolean checkAffiliated(String classroomId, String studentId) {

        EnrollmentStatus status = EnrollmentStatus.PERMITTED;

        Optional<EnrollmentEntity> entity = enrollmentJpaRepository.
                findByClassroom_ClassroomIdAndStudent_UserIdAndStatus(classroomId, studentId, status);

        log.info("해당 학생이 존재하는가? " + entity.isPresent());

        return entity.isPresent();
    }

    @Override
    public EnrollmentPageResultDto getList(String classroomId, int page) {

        PageRequestDto requestDto = PageRequestDto.builder()
                .page(page)
                .size(SIZE)
                .build();

        Page<EnrollmentEntity> pageResult = enrollmentJpaRepository.findByClassroom_ClassroomId(classroomId,
                requestDto.getPageable(Sort.by("regDate").descending()));

        Function<EnrollmentEntity, EnrollmentDto> fn = EnrollmentMapper.INSTANCE::toDto;

        PageResultDto<EnrollmentDto, EnrollmentEntity> pageResultDto = new PageResultDto(pageResult, fn);

        return EnrollmentMapper.INSTANCE.toPageResultDto(pageResultDto);
    }

    @Override
    public void refuse(Long id) {

        EnrollmentEntity entity = enrollmentJpaRepository.findById(id).orElseThrow();
        enrollmentJpaRepository.delete(entity);
    }
}
