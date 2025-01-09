package com.idealstudy.mvp.application.repository.inclass;

import com.idealstudy.mvp.application.dto.classroom.inclass.MaterialsDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.MaterialsPageResultDto;
import com.idealstudy.mvp.enums.classroom.MaterialsStatus;

import java.io.File;

public interface MaterialsRepository {

    MaterialsDto upload(String classroomId, String studentId, Integer orderNum, MaterialsStatus status,
                        String title, String description, String materialUri);

    MaterialsDto getDetail(Long id);

    /**
     * 해당 클래스룸의 PUBLIC 자료 목록 조회
     */
    MaterialsPageResultDto getListByClassroom(String classroomId, int page);

    /**
     * 해당 클래스룸 소속 학생들 중 특정 학생에게 올린 INDIVIDUAL 자료 목록 조회
     */
    MaterialsPageResultDto getListForStudent(String classroomId, String studentId, int page);

    /**
     * 해당 클래스룸에 올린 모든 PUBLIC, INDIVIDUAL 자료 목록 조회
     */
    MaterialsPageResultDto getListForTeacher(String classroomId, int page);

    MaterialsDto update(Long id, String studentId, Integer orderNum, MaterialsStatus status,
                        String description, String materialUri, String title);

    void delete(Long id);
}
