package com.idealstudy.mvp.unit.util;

import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;
import com.idealstudy.mvp.application.dto.member.TeacherDto;
import com.idealstudy.mvp.enums.classroom.ClassroomStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ServiceDummyClassGenerator {

    public static Map<String, Object> createDummy() {

        String teacherId = UUID.randomUUID().toString();
        TeacherDto teacherDto = ServiceDummyMemberGenerator.createDummyTeacherDto();

        String title = "test";
        String desc = "dummy";
        Integer capacity = 9;
        String thumbnail = "http://~";

        ClassroomResponseDto dummyDto = ClassroomResponseDto.builder()
                .id(UUID.randomUUID().toString())
                .title(title)
                .description(desc)
                .capacity(capacity)
                .createdBy(teacherId)
                .thumbnail(thumbnail)
                .status(ClassroomStatus.OPEN)
                .build();

        Map<String, Object> map = new HashMap<>();
        map.put("teacherDto", teacherDto);
        map.put("classroomResponseDto", dummyDto);

        return map;
    }
}
