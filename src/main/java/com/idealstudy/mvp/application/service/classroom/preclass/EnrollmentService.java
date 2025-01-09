package com.idealstudy.mvp.application.service.classroom.preclass;

import com.idealstudy.mvp.application.dto.classroom.preclass.EnrollmentDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.EnrollmentPageResultDto;
import com.idealstudy.mvp.application.repository.inclass.EnrollmentRepository;
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

    public EnrollmentDto enroll(String classroomId, String applicantId, String studentId, String curScore,
                                String targetScore, String request, String determination) {

        // 알림을 전달할 필요가 있다.

        return enrollmentRepository.enroll(classroomId, applicantId, studentId, curScore, targetScore,
                request, determination);
    }

    public void drop(Long id, String applicantId) {
        enrollmentRepository.drop(id, applicantId);
    }

    public EnrollmentDto check(Long id) {

        // 알림을 전달할 필요가 있다.

        return enrollmentRepository.check(id);
    }

    public EnrollmentDto getInfo(Long id) {
        return enrollmentRepository.getInfo(id);
    }

    public EnrollmentPageResultDto getList(String classroomId, int page) {
        return enrollmentRepository.getList(classroomId, page);
    }

    public void refuse(Long id) {

        // 알림을 전달할 필요가 있다.

        enrollmentRepository.refuse(id);
    }
}
