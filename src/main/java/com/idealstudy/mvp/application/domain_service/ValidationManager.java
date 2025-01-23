package com.idealstudy.mvp.application.domain_service;

import com.idealstudy.mvp.application.component.ClassroomComponent;
import com.idealstudy.mvp.application.component.EnrollmentComponent;
import com.idealstudy.mvp.enums.error.SecurityErrorMsg;
import com.idealstudy.mvp.error.CustomException;
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
     */
    public void validateStudentAffiliated(String classroomId, String studentId)
            throws CustomException {

        enrollmentComponent.checkAffiliated(classroomId, studentId);
    }

    /**
     * private 자료에 대한 소유권이 있는지 검사
     * @param userId
     * @param registeredUserId
     */
    public void validateIndividual(String userId, String registeredUserId)
            throws CustomException {

        if( !userId.equals(registeredUserId))
            throw new CustomException(SecurityErrorMsg.PRIVATE_EXCEPTION);
    }

    public void validateTeacher(String teacherId, String classroomId)
            throws CustomException{

        classroomComponent.checkMyClassroom(teacherId, classroomId);
    }
}