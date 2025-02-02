package com.idealstudy.mvp.application.component;

import com.idealstudy.mvp.enums.error.SecurityErrorMsg;
import com.idealstudy.mvp.application.repository.inclass.EnrollmentRepository;
import com.idealstudy.mvp.error.CustomException;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentComponent {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentComponent(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    /**
     * 해당 학생이 해당 클래스에 실제 등록되어 있는지를 검사. PERMITTED 상태일 때만 유효하다고 간주함.
     * EXPIRED 상태의 경우를 해당 학생이 해당 클래스에 등록되어 있다고 판정할지에 대해서는 고민 필요.
     * 일단 현재 활용 방식이 클래스가 활성화되어 있을 때 학생이 동작할 권한을 체크하는 것이므로 당장은 PERMITTED 상태로만 보는 것이 좋은듯.
     * @param classroomId
     * @param studentId
     */
    public void checkAffiliated(String classroomId, String studentId)
        throws CustomException {

        if( !enrollmentRepository.checkAffiliated(classroomId, studentId))
            throw new CustomException(SecurityErrorMsg.NOT_AFFILIATED);
    }
}
