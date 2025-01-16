package com.idealstudy.mvp.infrastructure.impl.classroom.inclass;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.EnrollmentDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.EnrollmentPageResultDto;
import com.idealstudy.mvp.enums.classroom.EnrollmentStatus;
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
    public EnrollmentDto enroll(String classroomId, String studentId, String curScore,
                                String targetScore, String request, String determination) {

        ClassroomEntity classroom = classroomJpaRepository.findById(classroomId).orElseThrow();

        StudentEntity student = studentJpaRepository.findById(studentId).orElseThrow();

        EnrollmentEntity entity = EnrollmentEntity.builder()
                .classroom(classroom)
                .student(student)
                .curScore(curScore)
                .targetScore(targetScore)
                .request(request)
                .determination(determination)
                .status(EnrollmentStatus.REQUEST)
                .build();

        return EnrollmentMapper.INSTANCE.toDto(enrollmentJpaRepository.save(entity));
    }

    @Override
    public void drop(Long id, String applicantId) {

        EnrollmentEntity entity = enrollmentJpaRepository.findById(id).orElseThrow();

        enrollmentJpaRepository.delete(entity);
    }

    @Override
    public EnrollmentDto accept(Long id) {

        EnrollmentEntity entity = enrollmentJpaRepository.findById(id).orElseThrow();
        entity.setStatus(EnrollmentStatus.CHECKED);

        return EnrollmentMapper.INSTANCE.toDto(enrollmentJpaRepository.save(entity));
    }

    @Override
    public EnrollmentDto getInfo(Long id) {

        EnrollmentEntity entity = enrollmentJpaRepository.findById(id).orElseThrow();

        return EnrollmentMapper.INSTANCE.toDto(entity);
    }

    /**
     * 추후 검색 조건이 필요하다고 판단하면 개선 필요
     * @param classroomId
     * @param page
     * @return
     */
    @Override
    public EnrollmentPageResultDto getListForTeacher(String classroomId, int page) {

        PageRequestDto requestDto = PageRequestDto.builder()
                .page(page)
                .size(SIZE)
                .build();

        Page<EnrollmentEntity> pageResult = enrollmentJpaRepository.findByClassroom_ClassroomId(classroomId,
                requestDto.getPageable(Sort.by("regDate").descending()));

        Function<EnrollmentEntity, EnrollmentDto> fn = EnrollmentMapper.INSTANCE::toDto;

        PageResultDto<EnrollmentDto, EnrollmentEntity> pageResultDto = new PageResultDto<>(pageResult, fn);

        return EnrollmentMapper.INSTANCE.toPageResultDto(pageResultDto);
    }

    @Override
    public EnrollmentPageResultDto getListForApplicant(String applicantId, int page) {

        PageRequestDto requestDto = PageRequestDto.builder()
                .page(page)
                .size(SIZE)
                .build();

        Page<EnrollmentEntity> pageResult = enrollmentJpaRepository.findByCreatedBy(applicantId,
                requestDto.getPageable(Sort.by("regDate").descending()));

        Function<EnrollmentEntity, EnrollmentDto> fn = EnrollmentMapper.INSTANCE::toDto;

        PageResultDto<EnrollmentDto, EnrollmentEntity> pageResultDto = new PageResultDto<>(pageResult, fn);

        return EnrollmentMapper.INSTANCE.toPageResultDto(pageResultDto);
    }

    @Override
    public EnrollmentDto update(Long id, String curScore, String targetScore, String request, String determination) {

        EnrollmentEntity entity = enrollmentJpaRepository.findById(id).orElseThrow();

        if (curScore != null) {
            entity.setCurScore(curScore);
        }
        if (targetScore != null) {
            entity.setTargetScore(targetScore);
        }
        if (request != null) {
            entity.setRequest(request);
        }
        if (determination != null) {
            entity.setDetermination(determination);
        }

        return EnrollmentMapper.INSTANCE.toDto(enrollmentJpaRepository.save(entity));
    }

    @Override
    public void reject(Long id) {

        EnrollmentEntity entity = enrollmentJpaRepository.findById(id).orElseThrow();

        /// 거절됨 상태를 만들어야 할듯?
        // enrollmentJpaRepository.delete(entity);

        entity.setStatus(EnrollmentStatus.REJECTED);
        enrollmentJpaRepository.save(entity);
    }

    @Override
    public boolean checkAffiliated(String classroomId, String studentId) {

        EnrollmentStatus status = EnrollmentStatus.PERMITTED;

        Optional<EnrollmentEntity> entity = enrollmentJpaRepository.
                findByClassroom_ClassroomIdAndStudent_UserIdAndStatus(classroomId, studentId, status);

        log.info("해당 학생이 존재하는가? " + entity.isPresent());

        return entity.isPresent();
    }
}
