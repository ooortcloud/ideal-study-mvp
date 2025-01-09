package com.idealstudy.mvp.application.service.classroom.inclass;

import com.idealstudy.mvp.application.dto.classroom.inclass.RecordLectureDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.RecordLecturePageResultDto;
import com.idealstudy.mvp.application.component.ClassroomComponent;
import com.idealstudy.mvp.application.component.EnrollmentComponent;
import com.idealstudy.mvp.enums.error.DBErrorMsg;
import com.idealstudy.mvp.application.repository.ClassroomRepository;
import com.idealstudy.mvp.application.repository.inclass.RecordLectureRepository;
import com.idealstudy.mvp.util.TryCatchServiceTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class RecordLectureService {

    private final RecordLectureRepository recordLectureRepository;

    private final EnrollmentComponent enrollmentComponent;

    private final ClassroomComponent classroomComponent;

    public RecordLectureService(RecordLectureRepository recordLectureRepository,
                                EnrollmentComponent enrollmentComponent,
                                ClassroomComponent classroomComponent) {
        this.recordLectureRepository = recordLectureRepository;
        this.enrollmentComponent = enrollmentComponent;
        this.classroomComponent = classroomComponent;
    }

    public RecordLectureDto create(String classroomId, String title, String description, String videoEndPoint,
                                   Integer order, String teacherId) {

        return TryCatchServiceTemplate.execute(() ->
                recordLectureRepository.create(classroomId, title, description, videoEndPoint, order),
                () -> classroomComponent.checkMyClassroom(teacherId, classroomId), DBErrorMsg.CREATE_ERROR);
    }

    public RecordLectureDto getDetail(Long id, String studentId) {

        return TryCatchServiceTemplate.execute(() -> {
            RecordLectureDto dto = recordLectureRepository.getDetail(id);

            enrollmentComponent.checkAffiliated(dto.getClassroomId(), studentId);

            return dto;
            },
        null, DBErrorMsg.SELECT_ERROR);
    }

    public RecordLecturePageResultDto selectList(String classroomId, int page) {

        return TryCatchServiceTemplate.execute(() -> recordLectureRepository.selectList(classroomId, page),
                null, DBErrorMsg.SELECT_ERROR);
    }

    public RecordLectureDto update(Long id, String title, String description, String videoEndPoint, Integer order
                    ,String teacherId, String classroomId) {

        return TryCatchServiceTemplate.execute(() -> recordLectureRepository.update(id, title, description,
                        videoEndPoint, order),
                () -> classroomComponent.checkMyClassroom(teacherId, classroomId), DBErrorMsg.UPDATE_ERROR);
    }

    public void delete(Long id, String teacherId, String classroomId) {

        TryCatchServiceTemplate.execute(() -> {
            recordLectureRepository.delete(id);
            return null;
        },() -> classroomComponent.checkMyClassroom(teacherId, classroomId), DBErrorMsg.DELETE_ERROR);
    }
}
