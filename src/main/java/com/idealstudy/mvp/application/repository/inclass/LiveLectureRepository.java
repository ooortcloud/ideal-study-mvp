package com.idealstudy.mvp.application.repository.inclass;

import com.idealstudy.mvp.application.dto.classroom.inclass.LiveLectureDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.LiveLecturePageResultDto;
import com.idealstudy.mvp.enums.classroom.Platform;

import java.time.LocalDateTime;

public interface LiveLectureRepository {

    LiveLectureDto create(String classroomId, String title, LocalDateTime startTime, LocalDateTime endTime,
                          String studySpace, Platform flatform);

    LiveLectureDto getOne(Long id);

    LiveLecturePageResultDto getList(String classroomId);

    LiveLectureDto update(Long id, String title, LocalDateTime startTime, LocalDateTime endTime,
                String studySpace, Platform flatform);

    void delete(Long id);
}
