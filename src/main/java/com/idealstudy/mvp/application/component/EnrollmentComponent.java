package com.idealstudy.mvp.application.component;

import com.idealstudy.mvp.enums.error.SecurityErrorMsg;
import com.idealstudy.mvp.application.repository.inclass.EnrollmentRepository;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentComponent {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentComponent(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public void checkAffiliated(String classroomId, String studentId)
        throws SecurityException{

        if( !enrollmentRepository.checkAffiliated(classroomId, studentId))
            throw new SecurityException(SecurityErrorMsg.NOT_AFFILIATED.toString());
    }
}
