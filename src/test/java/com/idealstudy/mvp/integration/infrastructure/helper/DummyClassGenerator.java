package com.idealstudy.mvp.integration.infrastructure.helper;

import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;
import com.idealstudy.mvp.application.dto.member.TeacherDto;
import com.idealstudy.mvp.application.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class DummyClassGenerator {

    private final ClassroomRepository classroomRepository;

    private final DummyMemberGenerator dummyMemberGenerator;

    @Autowired
    public DummyClassGenerator(ClassroomRepository classroomRepository, DummyMemberGenerator dummyMemberGenerator) {
        this.classroomRepository = classroomRepository;
        this.dummyMemberGenerator = dummyMemberGenerator;
    }

    public Map<String, Object> createDummy() {

        String teacherId = UUID.randomUUID().toString();
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
}
