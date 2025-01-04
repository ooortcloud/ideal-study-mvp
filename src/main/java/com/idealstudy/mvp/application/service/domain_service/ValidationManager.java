package com.idealstudy.mvp.application.service.domain_service;

import com.idealstudy.mvp.application.component.ClassroomComponent;
import com.idealstudy.mvp.application.component.EnrollmentComponent;
import com.idealstudy.mvp.enums.error.SecurityErrorMsg;
import org.springframework.stereotype.Service;

@Service
public class ValidationManager {

    private final EnrollmentComponent enrollmentComponent;

    private final ClassroomComponent classroomComponent;

    public ValidationManager(EnrollmentComponent enrollmentComponent, ClassroomComponent classroomComponent) {
        this.enrollmentComponent = enrollmentComponent;
        this.classroomComponent = classroomComponent;
    }

    /**
     * 어떤 학생이 해당 클래스 소속인지 검사
     * @param classroomId
     * @param studentId
     * @throws SecurityException
     */
    public void validateStudentAffiliated(String classroomId, String studentId)
            throws SecurityException {

        enrollmentComponent.checkAffiliated(classroomId, studentId);
    }

    /**
     * private 자료에 대한 소유권이 있는지 검사
     * @param userId
     * @param registeredUserId
     * @throws SecurityException
     */
    public void validateIndividual(String userId, String registeredUserId)
            throws SecurityException {

        if( !userId.equals(registeredUserId))
            throw new SecurityException(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
    }

    public void validateTeacher(String teacherId, String classroomId) {

        classroomComponent.checkMyClassroom(teacherId, classroomId);
    }
}