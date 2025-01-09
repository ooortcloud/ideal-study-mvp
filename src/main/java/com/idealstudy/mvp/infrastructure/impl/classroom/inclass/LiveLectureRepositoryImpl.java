package com.idealstudy.mvp.infrastructure.impl.classroom.inclass;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.LiveLectureDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.LiveLecturePageResultDto;
import com.idealstudy.mvp.enums.classroom.Platform;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.LiveLectureEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.ClassroomJpaRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.inclass.LiveLectureJpaRepository;
import com.idealstudy.mvp.application.repository.inclass.LiveLectureRepository;
import com.idealstudy.mvp.mapstruct.LiveLectureMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.function.Function;

@Repository
@Slf4j
@RequiredArgsConstructor
public class LiveLectureRepositoryImpl implements LiveLectureRepository {

    @Autowired
    private final LiveLectureJpaRepository liveLectureJpaRepository;

    @Autowired
    private final ClassroomJpaRepository classroomJpaRepository;

    private final static int SIZE = 9999;

    @Override
    public LiveLectureDto create(String classroomId, String title, LocalDateTime startTime, LocalDateTime endTime,
                                 String studySpace, Platform flatform) {

        ClassroomEntity classroom = classroomJpaRepository.findById(classroomId).orElseThrow();

        LiveLectureEntity entity = LiveLectureEntity.builder()
                    .classroom(classroom)
                    .title(title)
                    .startTime(startTime)
                    .endTime(endTime)
                    .studySpace(studySpace)
                    .platform(flatform)
                    .build();

        return LiveLectureMapper.INSTANCE.toDto(liveLectureJpaRepository.save(entity));
    }

    @Override
    public LiveLectureDto getOne(Long id) {

        LiveLectureEntity entity = liveLectureJpaRepository.findById(id).orElseThrow();
        return LiveLectureMapper.INSTANCE.toDto(entity);
    }

    @Override
    public LiveLecturePageResultDto getList(String classroomId) {

        PageRequestDto requestDto = PageRequestDto.builder()
                .page(1)
                .size(SIZE)
                .build();

        Page<LiveLectureEntity> pageResult = liveLectureJpaRepository.findByClassroom_classroomId(classroomId,
                requestDto.getPageable(Sort.by("startTime").ascending()));

        Function<LiveLectureEntity, LiveLectureDto> fn = LiveLectureMapper.INSTANCE::toDto;

        PageResultDto<LiveLectureDto, LiveLectureEntity> pageResultDto = new PageResultDto<>(pageResult, fn);

        return LiveLectureMapper.INSTANCE.toPageResultDto(pageResultDto);
    }

    @Override
    public LiveLectureDto update(Long id, String title, LocalDateTime startTime, LocalDateTime endTime, String studySpace,
                       Platform flatform) {

        LiveLectureEntity entity = liveLectureJpaRepository.findById(id).orElseThrow();

        if (title != null) {
            entity.setTitle(title);
        }
        if (startTime != null) {
            entity.setStartTime(startTime);
        }
        if (endTime != null) {
            entity.setEndTime(endTime);
        }
        if (studySpace != null) {
            entity.setStudySpace(studySpace);
        }
        if (flatform != null) {
            entity.setPlatform(flatform);
        }

        return LiveLectureMapper.INSTANCE.toDto(liveLectureJpaRepository.save(entity));
    }

    @Override
    public void delete(Long id) {

        LiveLectureEntity entity = liveLectureJpaRepository.findById(id).orElseThrow();
        liveLectureJpaRepository.delete(entity);
    }
}
