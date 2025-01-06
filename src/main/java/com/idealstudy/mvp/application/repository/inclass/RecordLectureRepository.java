package com.idealstudy.mvp.application.repository.inclass;

import com.idealstudy.mvp.application.dto.classroom.inclass.RecordLectureDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.RecordLecturePageResultDto;

import java.io.File;

public interface RecordLectureRepository {

    @Deprecated
    default RecordLectureDto create(String classroomId, String title, String description, File file, int order) {
        throw new UnsupportedOperationException("This method is deprecated and should not be used.");
    }

    RecordLectureDto create(String classroomId, String title, String description, String videoEndPoint, Integer order);

    RecordLectureDto getDetail(Long id);

    RecordLecturePageResultDto selectList(String classroomId, int page);

    /**
     * 파라미터에 null값을 넣으면, 해당 파라미터는 변경 대상에서 제외됨.
     */
    @Deprecated
    default void updateVideo(Long id, String title, String description, String videoUrl) {
        throw new UnsupportedOperationException("This method is deprecated and should not be used.");
    }

    void updateOrder(Long id, int order);

    RecordLectureDto update(Long id, String title, String description, String videoEndPoint, Integer order);

    @Deprecated
    default void deleteVideo(Long id, String videoEndPoint){
        throw new UnsupportedOperationException("This method is deprecated and should not be used.");
    }

    void delete(Long id);
}
