package com.idealstudy.mvp.infrastructure.impl.classroom.inclass;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.exam.*;
import com.idealstudy.mvp.application.repository.inclass.AssessmentRepository;
import com.idealstudy.mvp.enums.classroom.AssessmentType;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.AssessmentEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.ClassroomJpaRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.inclass.AssessmentJpaRepository;
import com.idealstudy.mvp.mapstruct.AssessmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class AssessmentRepositoryImpl implements AssessmentRepository {

    @Autowired
    private final AssessmentJpaRepository assessmentJpaRepository;

    @Autowired
    private final ClassroomJpaRepository classroomJpaRepository;

    private final int SIZE = 10;

    @Override
    public ExamDto createTextExam(String classroomId, AssessmentType ASSESSMENT_TYPE, String title, String description,
                                  LocalDateTime startTime, LocalDateTime endTime, String content) {

        ClassroomEntity classroom = classroomJpaRepository.findById(classroomId).orElseThrow();

        AssessmentEntity assessmentEntity = AssessmentEntity.builder()
                .classroom(classroom)
                .assessmentType(ASSESSMENT_TYPE)
                .title(title)
                .description(description)
                .startTime(startTime)
                .endTime(endTime)
                .examText(content)
                .build();

        return AssessmentMapper.INSTANCE.toExamDto(assessmentJpaRepository.save(assessmentEntity));
    }

    @Override
    public ExamDto createFileExam(String classroomId, AssessmentType ASSESSMENT_TYPE, String title, String description,
                                  LocalDateTime startTime, LocalDateTime endTime, String uri) {

        ClassroomEntity classroom = classroomJpaRepository.findById(classroomId).orElseThrow();

        AssessmentEntity assessmentEntity = AssessmentEntity.builder()
                .classroom(classroom)
                .assessmentType(ASSESSMENT_TYPE)
                .title(title)
                .description(description)
                .startTime(startTime)
                .endTime(endTime)
                .examUri(uri)
                .build();

        return AssessmentMapper.INSTANCE.toExamDto(assessmentJpaRepository.save(assessmentEntity));
    }

    @Override
    public ExamDto findByIdForExam(Long id) {

        AssessmentEntity entity = assessmentJpaRepository.findById(id).orElseThrow();
        return AssessmentMapper.INSTANCE.toExamDto(entity);
    }

    @Override
    public ExamPageResultDto<ExamListResponseDto> findListForExam(int page, String classroomId) {

        PageRequestDto requestDto = PageRequestDto.builder()
                .page(page)
                .size(SIZE)
                .build();

        Page<AssessmentEntity> pageEntity = assessmentJpaRepository.findByClassroom_classroomId(
                classroomId, requestDto.getPageable(Sort.by("startTime").ascending())
        );

        PageResultDto<ExamListResponseDto, AssessmentEntity> pageResultDto = new PageResultDto<>(
                pageEntity, AssessmentMapper.INSTANCE::toExamListResponseDto
        );

        return AssessmentMapper.INSTANCE.toPageResultDtoWithExamListResponseDto(pageResultDto);
    }

    @Override
    public ExamDto updateTextExam(Long id,  String title, String description, LocalDateTime startTime,
                                  LocalDateTime endTime, String content) {

        AssessmentEntity entity = assessmentJpaRepository.findById(id).orElseThrow();

        if(title != null)
            entity.setTitle(title);

        if(description != null)
            entity.setDescription(description);

        if(startTime != null)
            entity.setStartTime(startTime);

        if(endTime != null)
            entity.setEndTime(endTime);

        if(content != null)
            entity.setExamText(content);

        return AssessmentMapper.INSTANCE.toExamDto(assessmentJpaRepository.save(entity));
    }

    @Override
    public ExamDto updateFileExam(Long id, String title, String description, LocalDateTime startTime,
                                  LocalDateTime endTime, String uri) {

        AssessmentEntity entity = assessmentJpaRepository.findById(id).orElseThrow();

        if(title != null)
            entity.setTitle(title);

        if(description != null)
            entity.setDescription(description);

        if(startTime != null)
            entity.setStartTime(startTime);

        if(endTime != null)
            entity.setEndTime(endTime);

        if(uri != null)
            entity.setExamUri(uri);

        return AssessmentMapper.INSTANCE.toExamDto(assessmentJpaRepository.save(entity));

    }


    @Override
    public void delete(Long id) {

        AssessmentEntity entity = assessmentJpaRepository.findById(id).orElseThrow();

        assessmentJpaRepository.delete(entity);
    }

    @Override
    public void feedbackToAll(Long id, String feedbackStr) {

        AssessmentEntity entity = assessmentJpaRepository.findById(id).orElseThrow();

        entity.setFeedbackToAll(feedbackStr);

        assessmentJpaRepository.save(entity);
    }

    @Override
    public ExamPageResultDto<ExamFeedbackListResponseDto> getFeedbackToAllListForExam(String classroomId, int page) {

        PageRequestDto requestDto = PageRequestDto.builder()
                .page(page)
                .size(SIZE)
                .build();

        Page<AssessmentEntity> result = assessmentJpaRepository
                .findByClassroom_classroomIdAndAssessmentTypeAndFeedbackToAllIsNotNull(
                classroomId, AssessmentType.EXAM, requestDto.getPageable(Sort.by("id").descending()));

        PageResultDto<ExamFeedbackListResponseDto, AssessmentEntity> pageResultDto =
                new PageResultDto<>(result, AssessmentMapper.INSTANCE::toExamFeedbackListResponseDto);

        return AssessmentMapper.INSTANCE.toPageResultDtoWithExamFeedbackListResponseDto(pageResultDto);
    }

    @Override
    public ExamFeedbackDetailResponse getFeedbackToAllDetail(Long id) {

        AssessmentEntity entity = assessmentJpaRepository.findById(id).orElseThrow();

        return AssessmentMapper.INSTANCE.toExamFeedbackDetailResponse(entity);
    }


}
