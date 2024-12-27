package com.idealstudy.mvp.application.service.classroom.inclass.exam;


import com.idealstudy.mvp.application.dto.classroom.inclass.exam.ExamDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.exam.ExamFeedbackDetailResponse;
import com.idealstudy.mvp.application.dto.classroom.inclass.exam.ExamListResponseDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.exam.ExamPageResultDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.submission.SubmissionDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.submission.SubmissionFeedbackDetailResponse;
import com.idealstudy.mvp.application.dto.classroom.inclass.submission.SubmissionPageResultDto;
import com.idealstudy.mvp.application.repository.inclass.AssessmentRepository;
import com.idealstudy.mvp.application.repository.inclass.SubmissionRepository;
import com.idealstudy.mvp.application.service.domain_service.FileManager;
import com.idealstudy.mvp.application.service.domain_service.ValidationManager;
import com.idealstudy.mvp.domain.Assessment;
import com.idealstudy.mvp.enums.classroom.AssessmentType;
import com.idealstudy.mvp.enums.error.DBErrorMsg;
import com.idealstudy.mvp.mapstruct.SubmissionMapper;
import com.idealstudy.mvp.presentation.dto.classroom.SubmissionResponseDto;
import com.idealstudy.mvp.util.TryCatchServiceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// 비회원의 경우에도 사용할 수 있어야 하므로, 공통 컴포넌트도 인스턴스로 생성할 수 있어야 함.
@Component
public class ExamCommonService {

    protected final AssessmentRepository assessmentRepository;

    protected final SubmissionRepository submissionRepository;

    protected final ValidationManager validationManager;

    protected final FileManager fileManager;

    protected final Assessment assessment;

    protected final static AssessmentType ASSESSMENT_TYPE = AssessmentType.EXAM;

    @Autowired
    public ExamCommonService(AssessmentRepository assessmentRepository, SubmissionRepository submissionRepository,
                             ValidationManager validationManager, @Value("${upload.exam-path}") String uploadPath) {
        this.assessmentRepository = assessmentRepository;
        this.submissionRepository = submissionRepository;
        this.validationManager = validationManager;
        this.assessment = new Assessment();
        fileManager = new FileManager(uploadPath);
    }

    public ExamPageResultDto<ExamListResponseDto> getExamList(int page, String classroomId) {

        return TryCatchServiceTemplate.execute(() -> {
            ExamPageResultDto<ExamListResponseDto> pageResultDto =
                    assessmentRepository.findListForExam(page, classroomId);

            if( pageResultDto.getDtoList() != null)
                assessment.setAssessmentStatus(pageResultDto.getDtoList());

            return pageResultDto;
        },
        null, DBErrorMsg.SELECT_ERROR);
    }

}
