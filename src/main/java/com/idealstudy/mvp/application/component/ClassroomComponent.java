package com.idealstudy.mvp.application.component;

import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;
import com.idealstudy.mvp.enums.error.SecurityErrorMsg;
import com.idealstudy.mvp.application.repository.ClassroomRepository;
import org.springframework.stereotype.Component;

@Component
public class ClassroomComponent {

    private final ClassroomRepository classroomRepository;

    public ClassroomComponent(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    public void checkMyClassroom(String teacherId, String classroomId)
            throws SecurityException {

        ClassroomResponseDto dto = classroomRepository.findById(classroomId);
        if( !dto.getCreatedBy().equals(teacherId))
            throw new SecurityException(SecurityErrorMsg.NOT_YOURS.toString());
    }
}
