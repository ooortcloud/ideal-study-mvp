package com.idealstudy.mvp.application.repository.inclass;

import com.idealstudy.mvp.application.dto.classroom.inclass.exam.*;
import com.idealstudy.mvp.enums.classroom.AssessmentType;

import java.time.LocalDateTime;

public interface AssessmentRepository {

    ExamDto createTextExam(String classroomId, AssessmentType ASSESSMENT_TYPE, String title,
                           String description, LocalDateTime startTime, LocalDateTime endTime, String content);

    ExamDto createFileExam(String classroomId, AssessmentType ASSESSMENT_TYPE, String title,
                           String description, LocalDateTime startTime, LocalDateTime endTime, String uri);

    ExamDto findByIdForExam(Long id);

    ExamPageResultDto<ExamListResponseDto> findListForExam(int page, String classroomId);

    ExamDto updateTextExam(Long id, String title, String description,
                           LocalDateTime startTime, LocalDateTime endTime, String content);

    ExamDto updateFileExam(Long id,  String title,
                           String description, LocalDateTime startTime, LocalDateTime endTime, String uri);

    void delete(Long id);

    void feedbackToAll(Long id, String feedbackStr);

    ExamPageResultDto<ExamFeedbackListResponseDto> getFeedbackToAllListForExam(String classroomId, int page);

    ExamFeedbackDetailResponse getFeedbackToAllDetail(Long id);


}
