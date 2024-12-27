package com.idealstudy.mvp.application.service.classroom.inclass.exam;

import com.idealstudy.mvp.application.dto.classroom.inclass.exam.ExamFeedbackDetailResponse;
import com.idealstudy.mvp.application.dto.classroom.inclass.submission.*;
import com.idealstudy.mvp.application.dto.classroom.inclass.exam.ExamDto;
import com.idealstudy.mvp.application.repository.inclass.AssessmentRepository;
import com.idealstudy.mvp.application.repository.inclass.SubmissionRepository;
import com.idealstudy.mvp.application.service.domain_service.ValidationManager;
import com.idealstudy.mvp.enums.classroom.SubmissionStatus;
import com.idealstudy.mvp.enums.error.DBErrorMsg;
import com.idealstudy.mvp.mapstruct.SubmissionMapper;
import com.idealstudy.mvp.presentation.dto.classroom.SubmissionResponseDto;
import com.idealstudy.mvp.util.TryCatchServiceTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;

@Service
@Slf4j
@Transactional
public class ExamStudentService extends ExamCommonService {


    public ExamStudentService(AssessmentRepository assessmentRepository, ValidationManager validationManager,
                              SubmissionRepository submissionRepository,
                              @Value("${upload.exam-path}") String uploadPath) {
        super(assessmentRepository, submissionRepository, validationManager, uploadPath);
    }

    public String takeText(Long assessmentId, String studentId) {

        return TryCatchServiceTemplate.execute(() -> {
            ExamDto examDto = assessmentRepository.findByIdForExam(assessmentId);

            // 조회가 된다 = 자격이 있다
            // token 기반으로 사용자 정보를 받아오기 때문에 안전한 로직임
            SubmissionDto submissionDto = submissionRepository.findByAssessmentIdAndStudentId(assessmentId, studentId);
            submissionRepository.updateStatus(submissionDto.getId(), SubmissionStatus.IN_PROGRESS);

            return examDto.getExamText();
        }, null , DBErrorMsg.SELECT_ERROR);
    }

    public File takeFile(Long assessmentId, String studentId) {

        return TryCatchServiceTemplate.execute(() -> {

            ExamDto examDto = assessmentRepository.findByIdForExam(assessmentId);

            SubmissionDto submissionDto = submissionRepository.findByAssessmentIdAndStudentId(assessmentId, studentId);
            submissionRepository.updateStatus(submissionDto.getId(), SubmissionStatus.IN_PROGRESS);

            return fileManager.getFile(examDto.getExamUri());
        }, null, DBErrorMsg.SELECT_ERROR);
    }

    public ExamDto getExamDetail(Long id, String studentId, String classroomId) {

        return TryCatchServiceTemplate.execute(() ->
                        assessmentRepository.findByIdForExam(id),
                () -> validationManager.validateStudentAffiliated(classroomId, studentId), DBErrorMsg.SELECT_ERROR);
    }

    public SubmissionDto submit(Long assessmentId, String studentId, String text) {

        return TryCatchServiceTemplate.execute(() -> {

            SubmissionDto dto = submissionRepository.findByAssessmentIdAndStudentId(assessmentId, studentId);

            validationManager.validateStudentIndividual(studentId, dto.getStudentId());

            return submissionRepository.updateSubmissionText(dto.getId(), text);
        }, null , DBErrorMsg.UPDATE_ERROR);
    }

    public SubmissionDto submit(Long assessmentId, String studentId, InputStream inputStream, String originalFileName) {

        return TryCatchServiceTemplate.execute(() -> {

            SubmissionDto dto = submissionRepository.findByAssessmentIdAndStudentId(assessmentId, studentId);

            validationManager.validateStudentIndividual(studentId, dto.getStudentId());

            String uri = fileManager.saveFile(inputStream, originalFileName);

            return submissionRepository.updateSubmissionUri(dto.getId(), uri);
        }, null , DBErrorMsg.UPDATE_ERROR);
    }

    public void modifySubmission(Long assessmentId, String studentId, String text) {

        submit(assessmentId, studentId, text);
    }

    public void modifySubmission(Long assessmentId, String studentId, InputStream inputStream, String originalFileName) {

        TryCatchServiceTemplate.execute(() -> {

            SubmissionDto dto = submissionRepository.findByAssessmentIdAndStudentId(assessmentId, studentId);

            validationManager.validateStudentIndividual(studentId, dto.getStudentId());

            fileManager.deleteFile(dto.getSubmissionUri());

            String uri = fileManager.saveFile(inputStream, originalFileName);

            submissionRepository.updateSubmissionUri(dto.getId(), uri);

            return null;
        }, null , DBErrorMsg.UPDATE_ERROR);
    }

    public SubmissionPageResultDto<SubmissionListResponseDto> getSubmissionList(String studentId, int page) {

        return TryCatchServiceTemplate.execute(() -> submissionRepository.findByStudentId(studentId, page),
                null, DBErrorMsg.SELECT_ERROR);
    }

    public SubmissionResponseDto getSubmissionDetail(Long assessmentId, String studentId) {

        return TryCatchServiceTemplate.execute(() -> {
                    SubmissionDto dto = submissionRepository.findByAssessmentIdAndStudentId(assessmentId, studentId);

                    validationManager.validateStudentIndividual(studentId, dto.getStudentId());

                    return SubmissionMapper.INSTANCE.toResponseDto(dto);
                },
                null, DBErrorMsg.SELECT_ERROR);
    }

    public ExamFeedbackDetailResponse getFeedbackToAllDetail(Long id, String studentId, String classroomId) {

        return TryCatchServiceTemplate.execute(() -> {

                    ExamDto dto = assessmentRepository.findByIdForExam(id);
                    if(dto.getFeedbackToAll() == null)
                        throw new IllegalArgumentException("아직 전체 피드백이 작성되지 않은 시험입니다.");

                    return assessmentRepository.getFeedbackToAllDetail(id);
                },
                () -> validationManager.validateStudentAffiliated(classroomId, studentId), DBErrorMsg.SELECT_ERROR);
    }

    public SubmissionPageResultDto<SubmissionFeedbackListResponseDto> getPersonalityFeedbackList(String studentId,
                                                                                                 int page) {

        // 어차피 여기서는 token 정보를 기준으로 검색할 것이기 때문에, private 유효성 검사가 불필요함. 
        // token에 일치하는 정보가 없으면 그저 해당 학생에 대한 데이터가 없는 거니까.
        return TryCatchServiceTemplate.execute(() ->
            submissionRepository.findPersonalityFeedbackListByStudentId(studentId, page),
        null, DBErrorMsg.SELECT_ERROR);
    }

    public SubmissionFeedbackDetailResponse getPersonalityFeedbackDetail() {

        return null;
    }
}
