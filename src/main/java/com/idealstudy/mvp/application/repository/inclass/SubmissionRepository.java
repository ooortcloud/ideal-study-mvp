package com.idealstudy.mvp.application.repository.inclass;

import com.idealstudy.mvp.application.dto.classroom.inclass.submission.SubmissionDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.submission.SubmissionFeedbackListResponseDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.submission.SubmissionListResponseDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.submission.SubmissionPageResultDto;
import com.idealstudy.mvp.enums.classroom.AssessmentType;
import com.idealstudy.mvp.enums.classroom.SubmissionStatus;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface SubmissionRepository {

    void insert(Long assessmentId, List<String> studentList, AssessmentType type,
                LocalDateTime startedAt, LocalDateTime endedAt) throws SQLException;

    SubmissionDto findById(Long id);


    SubmissionPageResultDto<SubmissionListResponseDto> findByStudentId(String studentId, int page);

    SubmissionPageResultDto<SubmissionListResponseDto> findByClassroomId(String classroomId, int page);

    SubmissionDto findByAssessmentIdAndStudentId(Long assessmentId, String studentId);

    SubmissionPageResultDto<SubmissionFeedbackListResponseDto> findPersonalityFeedbackListByStudentId(
            String studentId, int page);

    SubmissionPageResultDto<SubmissionFeedbackListResponseDto> findPersonalityFeedbackListByClassroomId(
            String classroomId, int page);

    SubmissionDto updateStatus(Long id, SubmissionStatus status);

    SubmissionDto updateSubmissionText(Long id, String text);

    SubmissionDto updateSubmissionUri(Long id, String uri);

    SubmissionDto updateScore(Long assessmentId, String studentId, int score);

    SubmissionDto updatePersonalFeedback(Long assessmentId, String studentId, String feedback);
}
