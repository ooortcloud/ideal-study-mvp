package com.idealstudy.mvp.integration.infrastructure.helper;

import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;
import com.idealstudy.mvp.application.dto.member.TeacherDto;
import com.idealstudy.mvp.application.repository.ClassroomRepository;
import com.idealstudy.mvp.enums.member.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class InfraDummyClassGenerator {

    private final ClassroomRepository classroomRepository;

    private final InfraDummyMemberGenerator dummyMemberGenerator;

    @Autowired
    public InfraDummyClassGenerator(ClassroomRepository classroomRepository, InfraDummyMemberGenerator dummyMemberGenerator) {
        this.classroomRepository = classroomRepository;
        this.dummyMemberGenerator = dummyMemberGenerator;
    }

    public Map<String, Object> createDummy() {

        String teacherId = UUID.randomUUID().toString();
        dummyMemberGenerator.setToken(teacherId, Role.ROLE_TEACHER);
        TeacherDto teacherDto = dummyMemberGenerator.createDummyTeacher(teacherId);

        String title = "test";
        String desc = "dummy";
        Integer capacity = 9;
        String thumbnail = "http://~";
        ClassroomResponseDto responseDto = classroomRepository.create(title, desc, capacity, thumbnail, teacherId);

        Map<String, Object> map = new HashMap<>();
        map.put("teacherDto", teacherDto);
        map.put("classroomResponseDto", responseDto);

        return map;
    }

    /**
     * 리스트에 사용할 더미데이터 생성 시 활용해보세요.
     * @param cnt
     * @return
     */
    public Map<String, Object> createDummy(int cnt) {

        String teacherId = UUID.randomUUID().toString();
        TeacherDto teacherDto = dummyMemberGenerator.createDummyTeacher(teacherId);

        String title = "test" + cnt;
        String desc = "dummy" + cnt;
        Integer capacity = cnt;
        String thumbnail = "http://~";
        ClassroomResponseDto responseDto = classroomRepository.create(title, desc, capacity, thumbnail, teacherId);

        Map<String, Object> map = new HashMap<>();
        map.put("teacherDto", teacherDto);
        map.put("classroomResponseDto", responseDto);

        return map;
    }
}
