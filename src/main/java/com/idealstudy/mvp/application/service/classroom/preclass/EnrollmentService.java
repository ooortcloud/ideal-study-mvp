package com.idealstudy.mvp.application.service.classroom.preclass;

import com.idealstudy.mvp.application.dto.classroom.preclass.EnrollmentDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.EnrollmentPageResultDto;
import com.idealstudy.mvp.application.repository.inclass.EnrollmentRepository;
import com.idealstudy.mvp.application.service.domain_service.ValidationManager;
import com.idealstudy.mvp.enums.classroom.EnrollmentStatus;
import com.idealstudy.mvp.enums.error.DBErrorMsg;
import com.idealstudy.mvp.enums.error.ErrorCode;
import com.idealstudy.mvp.error.CustomException;
import com.idealstudy.mvp.util.TryCatchServiceTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EnrollmentService {

    @Autowired
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    private final ValidationManager validationManager;

    public EnrollmentDto requestForStudent(String classroomId, String studentId, String curScore,
                                           String targetScore, String request, String determination) {

        return TryCatchServiceTemplate.execute(() -> {

            // 강사에게 알림을 전달할 필요가 있다.

            return enrollmentRepository.request(classroomId, studentId, curScore, targetScore,
                    request, determination);
        }, null, DBErrorMsg.CREATE_ERROR);

    }

    public EnrollmentDto requestForParents(String classroomId, String studentId, String curScore,
                                           String targetScore, String request, String determination) {

        return TryCatchServiceTemplate.execute(() -> {

            // applicantId와 studentId가 다른 경우: 학부모의 신청
            ///TODO: 신청한 학부모가 정말 해당 학생의 학부모인지 체크하는 로직 필요  << 추가 기능: 현재는 학부모 로직을 배제하여 개발하자.(설계가 하나도 되어 있지 않음)

            // 강사에게 알림을 전달할 필요가 있다.

            return enrollmentRepository.request(classroomId, studentId, curScore, targetScore,
                    request, determination);
        }, null, DBErrorMsg.CREATE_ERROR);

    }

    /**
     * 신청자가 신청을 포기하는 경우
     * @param id
     * @param applicantId
     */
    public void drop(Long id, String applicantId) {

        TryCatchServiceTemplate.execute(() -> {

            EnrollmentDto dto = enrollmentRepository.getInfo(id);

            // DB에 저장된 신청자와 요청자의 id가 서로 동일하지 않는 경우 로직 실행 거부
            validationManager.validateIndividual(applicantId, dto.getCreatedBy());

            enrollmentRepository.drop(id, applicantId);

            return null;
        }, null, DBErrorMsg.DELETE_ERROR);

    }

    /// 강사가 수업 신청을 승인하는 경우
    /// PERMITTED로 변경되는 기능은 결제 로직에서 담당해야 하며, EXPIRED로 변경되려면 클래스룸 기능에서 담당해야 한다.
    public EnrollmentDto accept(Long id, String teacherId) {

        return TryCatchServiceTemplate.execute(() -> {

            EnrollmentDto findDto = enrollmentRepository.getInfo(id);

            validationManager.validateTeacher(teacherId, findDto.getClassroomId());

            if(findDto.getStatus() == EnrollmentStatus.CHECKED)
                throw new CustomException(ErrorCode.ALREADY_PROCEEDED);

            if(findDto.getStatus() != EnrollmentStatus.REQUEST)
                throw new CustomException(ErrorCode.ABNORMAL_REQUEST);

            // 신청자에게 알림을 전달할 필요가 있다.

            return enrollmentRepository.accept(id);
        }, null, DBErrorMsg.UPDATE_ERROR);
    }

    public EnrollmentDto getInfoForTeacher(Long id, String teacherId) {

        return TryCatchServiceTemplate.execute(() -> {

            EnrollmentDto dto = enrollmentRepository.getInfo(id);
            validationManager.validateTeacher(teacherId, dto.getClassroomId());

            return dto;
        },
        null, DBErrorMsg.SELECT_ERROR);
    }

    public EnrollmentDto getInfoForStudent(Long id, String studentId) {

        return TryCatchServiceTemplate.execute(() ->  {

            EnrollmentDto dto = enrollmentRepository.getInfo(id);

            validationManager.validateIndividual(studentId, dto.getStudentId());

            return dto;
        },
        null, DBErrorMsg.SELECT_ERROR);
    }

    public EnrollmentDto getInfoForParents(Long id, String parentsId) {

        return TryCatchServiceTemplate.execute(() -> {

            EnrollmentDto dto = enrollmentRepository.getInfo(id);

            validationManager.validateIndividual(parentsId, dto.getCreatedBy());

            return dto;
        },
        null, DBErrorMsg.SELECT_ERROR);
    }

    public EnrollmentPageResultDto getListForTeacher(String classroomId, int page) {

        return TryCatchServiceTemplate.execute(() -> enrollmentRepository.getListForTeacher(classroomId, page),
                null, DBErrorMsg.SELECT_ERROR);
    }

    public EnrollmentPageResultDto getListForStudent(String studentId, int page) {

        return TryCatchServiceTemplate.execute(() -> {

            return enrollmentRepository.getListForApplicant(studentId, page);
        },
        null, DBErrorMsg.SELECT_ERROR);
    }

    public EnrollmentPageResultDto getListForParents(String parentsId, int page) {

        return TryCatchServiceTemplate.execute(() -> {

            return enrollmentRepository.getListForApplicant(parentsId, page);
        }, null, DBErrorMsg.SELECT_ERROR);
    }

    public EnrollmentDto update(Long id, String applicantId, String curScore, String targetScore,
                                String request, String determination) {

        return TryCatchServiceTemplate.execute(() -> {

            EnrollmentDto findDto = enrollmentRepository.getInfo(id);

            validationManager.validateIndividual(applicantId, findDto.getCreatedBy());

            return enrollmentRepository.update(id, curScore, targetScore, request, determination);

        }, null, DBErrorMsg.UPDATE_ERROR);
    }

    /**
     * 강사가 신청을 거부하는 경우
     * @param id
     */
    public void reject(Long id, String teacherId) {

        TryCatchServiceTemplate.execute(() -> {

            EnrollmentDto findDto = enrollmentRepository.getInfo(id);

            validationManager.validateTeacher(teacherId, findDto.getClassroomId());

            if(findDto.getStatus() == EnrollmentStatus.REJECTED)
                throw new CustomException(ErrorCode.ALREADY_PROCEEDED);

            if(findDto.getStatus() != EnrollmentStatus.REQUEST)
                throw new CustomException(ErrorCode.ABNORMAL_REQUEST);

            enrollmentRepository.reject(id);

            // 신청자에게 알림을 전달할 필요가 있다.

            return null;
        },
        null, DBErrorMsg.SELECT_ERROR);
    }
    
    /// 추가기능: 강사가 강제로 특정 학생을 수강 취소시킬 수 있음
    public void kick() {
        
    }

    public void enrollForStudent(Long enrollmentId, String studentId) {

        TryCatchServiceTemplate.execute(() -> {
            
            EnrollmentDto findDto = enrollmentRepository.getInfo(enrollmentId);
            
            /// 결제자와 신청자가 꼭 같아야 할까?

            if(findDto.getStatus() == EnrollmentStatus.PERMITTED)
                throw new CustomException(ErrorCode.ALREADY_PROCEEDED);

            if(findDto.getStatus() != EnrollmentStatus.CHECKED)
                throw new CustomException(ErrorCode.ABNORMAL_REQUEST);

            /// 결제 로직을 거쳐야 함(외부 결제 서버와 통신하는 로직 구현 필요)



            /// PERMITTED로 변경
            enrollmentRepository.permit(enrollmentId);

            return null;
            
        }, null, DBErrorMsg.UPDATE_ERROR);
    }

    public void enrollForParents(Long enrollmentId, String parentsId) {

        TryCatchServiceTemplate.execute(() -> {

            EnrollmentDto findDto = enrollmentRepository.getInfo(enrollmentId);

            /// 결제자와 신청자가 꼭 같아야 할까?

            if(findDto.getStatus() == EnrollmentStatus.PERMITTED)
                throw new CustomException(ErrorCode.ALREADY_PROCEEDED);

            if(findDto.getStatus() != EnrollmentStatus.CHECKED)
                throw new CustomException(ErrorCode.ABNORMAL_REQUEST);

            /// 결제 로직을 거쳐야 함(외부 결제 서버와 통신하는 로직 구현 필요)

            /// PERMITTED로 변경
            enrollmentRepository.permit(enrollmentId);

            return null;

        }, null, DBErrorMsg.UPDATE_ERROR);
    }
}
