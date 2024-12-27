package com.idealstudy.mvp.application.service.classroom.inclass.exam;

import com.idealstudy.mvp.application.dto.classroom.inclass.exam.ExamDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.exam.ExamFeedbackDetailResponse;
import com.idealstudy.mvp.application.dto.classroom.inclass.exam.ExamFeedbackListResponseDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.exam.ExamPageResultDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.submission.*;
import com.idealstudy.mvp.application.repository.inclass.AssessmentRepository;
import com.idealstudy.mvp.application.repository.inclass.SubmissionRepository;
import com.idealstudy.mvp.application.service.domain_service.ValidationManager;
import com.idealstudy.mvp.enums.error.DBErrorMsg;
import com.idealstudy.mvp.mapstruct.SubmissionMapper;
import com.idealstudy.mvp.presentation.dto.classroom.SubmissionResponseDto;
import com.idealstudy.mvp.util.TimeUtil;
import com.idealstudy.mvp.util.TryCatchServiceTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional
public class ExamTeacherService extends ExamCommonService{

    public ExamTeacherService(AssessmentRepository assessmentRepository, ValidationManager validationManager,
                              SubmissionRepository submissionRepository,
                              @Value("${upload.exam-path}") String uploadPath) {
        super(assessmentRepository, submissionRepository, validationManager, uploadPath);
    }

    public ExamDto create(String content, String teacherId, String classroomId, String title, String description,
                          LocalDateTime startTime, LocalDateTime endTime, List<String> studentList) {

        return TryCatchServiceTemplate.execute(() -> {

            TimeUtil.checkRightDuration(startTime, endTime);

            ExamDto dto = assessmentRepository.createTextExam(classroomId, ASSESSMENT_TYPE,
                            title, description, startTime, endTime, content);

            submissionRepository.insert(dto.getId(), studentList, ASSESSMENT_TYPE, startTime, endTime);

            return dto;
            },
        () -> validationManager.validateTeacher(teacherId, classroomId), DBErrorMsg.CREATE_ERROR);

    }

    public ExamDto create(InputStream inputStream, String originalFileName, String teacherId, String classroomId,
                       String title, String description, LocalDateTime startTime, LocalDateTime endTime,
                          List<String> studentList) {

        return TryCatchServiceTemplate.execute(() -> {

            TimeUtil.checkRightDuration(startTime, endTime);

            String uri = fileManager.saveFile(inputStream, originalFileName);

            try {
                ExamDto dto = assessmentRepository.createFileExam(classroomId, ASSESSMENT_TYPE, title,
                        description, startTime, endTime, uri);

                submissionRepository.insert(dto.getId(), studentList, ASSESSMENT_TYPE, startTime, endTime);

                return dto;

            } catch (Exception e) {
                fileManager.deleteFile(uri);
                throw new Exception(e);
            }
        },
        () -> validationManager.validateTeacher(teacherId, classroomId), DBErrorMsg.CREATE_ERROR);
    }


    public ExamDto getExamDetail(Long id, String teacherId, String classroomId) {

        return TryCatchServiceTemplate.execute(() -> assessmentRepository.findByIdForExam(id),
                () -> validationManager.validateTeacher(teacherId, classroomId), DBErrorMsg.SELECT_ERROR);
    }

    public ExamDto updateTextExam(Long id, String title, String description, LocalDateTime startTime,
                          LocalDateTime endTime, String content, String classroomId, String teacherId) {

        return TryCatchServiceTemplate.execute(() -> {

            ExamDto dto = assessmentRepository.findByIdForExam(id);

            if(endTime != null && startTime == null)
                TimeUtil.checkRightDuration(dto.getStartTime(), endTime);

            if(startTime != null && endTime == null)
                TimeUtil.checkRightDuration(startTime, dto.getEndTime());

            if(endTime != null && startTime != null)
                TimeUtil.checkRightDuration(startTime, endTime);

            return assessmentRepository.updateTextExam(
                    id, title, description, startTime, endTime, content
            );
        }, () -> validationManager.validateTeacher(teacherId, classroomId), DBErrorMsg.UPDATE_ERROR);
    }

    public ExamDto updateFileExam(Long id, String title, String description, LocalDateTime startTime,
                                  LocalDateTime endTime, String teacherId, String classroomId,
                                  InputStream inputStream, String originalFileName) {

        return TryCatchServiceTemplate.execute(() -> {

            ExamDto dto = assessmentRepository.findByIdForExam(id);

            if(endTime != null && startTime == null)
                TimeUtil.checkRightDuration(dto.getStartTime(), endTime);

            if(startTime != null && endTime == null)
                TimeUtil.checkRightDuration(startTime, dto.getEndTime());

            if(endTime != null && startTime != null)
                TimeUtil.checkRightDuration(startTime, endTime);

            fileManager.deleteFile(dto.getExamUri());

            final String newUri = fileManager.saveFile(inputStream, originalFileName);

            return assessmentRepository.updateFileExam(
                    id, title, description, startTime, endTime, newUri
            );
        }, () -> validationManager.validateTeacher(teacherId, classroomId), DBErrorMsg.UPDATE_ERROR);
    }

    public void deleteTextExam(Long id, String teacherId, String classroomId) {

        TryCatchServiceTemplate.execute(() -> {
            assessmentRepository.delete(id);
            return null;
        },
        () -> validationManager.validateTeacher(teacherId, classroomId), DBErrorMsg.DELETE_ERROR);
    }

    public void deleteFileExam(Long id, String teacherId, String classroomId) {

        TryCatchServiceTemplate.execute(() -> {

            ExamDto dto = assessmentRepository.findByIdForExam(id);
            String uri = dto.getExamUri();
            fileManager.deleteFile(uri);

            assessmentRepository.delete(id);
            return null;
        },
        () -> validationManager.validateTeacher(teacherId, classroomId), DBErrorMsg.DELETE_ERROR);
    }

    public void feedbackToAll(Long id, String feedbackStr, String teacherId, String classroomId) {

        TryCatchServiceTemplate.execute(() -> {

            assessmentRepository.feedbackToAll(id, feedbackStr);
            return null;
        },
        () -> validationManager.validateTeacher(teacherId, classroomId), DBErrorMsg.UPDATE_ERROR);
    }

    /**
     * 강사가 자신이 남긴 전체 피드백을 조회하기 위함
     */
    public ExamPageResultDto<ExamFeedbackListResponseDto> getFeedbackList(String teacherId, String classroomId,
                                                                          int page) {

        return TryCatchServiceTemplate.execute(() -> assessmentRepository.getFeedbackToAllListForExam(classroomId, page),
                () -> validationManager.validateTeacher(teacherId, classroomId), DBErrorMsg.SELECT_ERROR);
    }

    public ExamFeedbackDetailResponse getFeedbackToAllDetail(Long id, String teacherId, String classroomId) {

        return TryCatchServiceTemplate.execute(() -> {

                    ExamDto dto = assessmentRepository.findByIdForExam(id);
                    if(dto.getFeedbackToAll() == null)
                        throw new IllegalArgumentException("아직 전체 피드백이 작성되지 않은 시험입니다.");

                    return  assessmentRepository.getFeedbackToAllDetail(id);
                },
                () -> validationManager.validateTeacher(teacherId, classroomId), DBErrorMsg.SELECT_ERROR);
    }

    public void updateFeedbackToAll(Long id, String feedbackStr, String teacherId, String classroomId) {

        TryCatchServiceTemplate.execute(() -> {
            feedbackToAll(id, feedbackStr, teacherId, classroomId);
            return null;
        },
        null, DBErrorMsg.UPDATE_ERROR);
    }

    public void deleteFeedbackToAll(Long id, String teacherId, String classroomId) {

        TryCatchServiceTemplate.execute(() -> {
            feedbackToAll(id, "", teacherId, classroomId);
            return null;
        },
        null, DBErrorMsg.UPDATE_ERROR);
    }

    public SubmissionPageResultDto<SubmissionListResponseDto> getSubmissionList(String teacherId, String classroomId, int page) {

        return TryCatchServiceTemplate.execute(() -> submissionRepository.findByClassroomId(classroomId, page),
                () -> validationManager.validateTeacher(teacherId, classroomId), DBErrorMsg.SELECT_ERROR);
    }

    public SubmissionResponseDto getSubmissionDetail(Long assessmentId, String studentId,
                                                     String teacherId, String classroomId) {

        return TryCatchServiceTemplate.execute(() -> {

                    SubmissionDto dto = submissionRepository.findByAssessmentIdAndStudentId(assessmentId, studentId);

                    return SubmissionMapper.INSTANCE.toResponseDto(dto);
                },
                () -> validationManager.validateTeacher(teacherId, classroomId), DBErrorMsg.SELECT_ERROR);
    }

    public void grade(Long assessmentId, String studentId, int score, String teacherId, String classroomId) {

        TryCatchServiceTemplate.execute(() -> submissionRepository.updateScore(assessmentId, studentId, score),
                () -> validationManager.validateTeacher(teacherId, classroomId), DBErrorMsg.UPDATE_ERROR);
    }

    public void personalFeedback(Long assessmentId, String studentId,String feedback,
                               String teacherId, String classroomId) {

        TryCatchServiceTemplate.execute(() -> submissionRepository
                        .updatePersonalFeedback(assessmentId, studentId, feedback),
                () -> validationManager.validateTeacher(teacherId, classroomId), DBErrorMsg.UPDATE_ERROR);
    }


    public void updatePersonalFeedback(Long assessmentId, String studentId,String feedback,
                                       String teacherId, String classroomId) {

        personalFeedback(assessmentId, studentId, feedback, teacherId, classroomId);
    }

    public void deletePersonalFeedback(Long assessmentId, String studentId,
                                       String teacherId, String classroomId) {

        personalFeedback(assessmentId, studentId, null, teacherId, classroomId);
    }


    public SubmissionPageResultDto<SubmissionFeedbackListResponseDto> getPersonalityFeedbackList(String classroomId, int page, String teacherId) {

        return TryCatchServiceTemplate.execute(() -> submissionRepository.findPersonalityFeedbackListByClassroomId(
                classroomId, page),
                () -> validationManager.validateTeacher(teacherId, classroomId), DBErrorMsg.SELECT_ERROR);
    }


    public SubmissionFeedbackDetailResponse getPersonalityFeedbackDetail() {

        return null;
    }
}
