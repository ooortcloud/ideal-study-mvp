package com.idealstudy.mvp.application.component;

import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;
import com.idealstudy.mvp.enums.error.SecurityErrorMsg;
import com.idealstudy.mvp.application.repository.ClassroomRepository;
import com.idealstudy.mvp.error.CustomException;
import org.springframework.stereotype.Component;

@Component
public class ClassroomComponent {

    private final ClassroomRepository classroomRepository;

    public ClassroomComponent(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    public void checkMyClassroom(String teacherId, String classroomId)
            throws CustomException {

        ClassroomResponseDto dto = classroomRepository.findById(classroomId);
        if( !dto.getCreatedBy().equals(teacherId))
            throw new CustomException(SecurityErrorMsg.NOT_YOURS);
    }
}
