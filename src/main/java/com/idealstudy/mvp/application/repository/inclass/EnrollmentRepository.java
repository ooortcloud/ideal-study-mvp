package com.idealstudy.mvp.application.repository.inclass;

import com.idealstudy.mvp.application.dto.classroom.preclass.EnrollmentDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.EnrollmentPageResultDto;

public interface EnrollmentRepository {

    /**
     * 신청자(학생 또는 학부모)가 수업 신청을 함
     */
    EnrollmentDto enroll(String classroomId, String applicantId, String studentId, String curScore, String targetScore, String request, String determination);

    /**
     * 신청자(학생 또는 학부모)가 수업 신청을 포기함.
     * 학부모가 신청한 경우, 학부모에게만 수업 신청 포기 권한이 있음.
     * 학생이 신청한 경우, 학부모는 수업 신청 포기 권한이 없음.
     */
    void drop(Long id, String applicantId);

    /**
     * 강사가 enroll된 신청을 받아들임.
     * 신청자의 '결제' 상태를 대기.
     */
    EnrollmentDto check(Long id);

    /**
     * 특정 수업 신청 정보 확인
     */
    EnrollmentDto getInfo(Long id);

    boolean checkAffiliated(String classroomId, String studentId);

    /**
     * 특정 클래스의 수업 신청 리스트 조회
     */
    EnrollmentPageResultDto getList(String classroomId, int page);

    /**
     * 신청자의 enroll을 거절함.
     */
    void refuse(Long id);
}
