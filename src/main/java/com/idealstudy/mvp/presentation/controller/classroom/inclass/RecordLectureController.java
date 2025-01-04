package com.idealstudy.mvp.presentation.controller.classroom.inclass;

import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.RecordLectureDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.RecordLecturePageResultDto;
import com.idealstudy.mvp.application.service.classroom.inclass.RecordLectureService;
import com.idealstudy.mvp.enums.error.SecurityErrorMsg;
import com.idealstudy.mvp.security.annotation.ForTeacher;
import com.idealstudy.mvp.security.annotation.ForUser;
import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import com.idealstudy.mvp.util.TryCatchControllerTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class RecordLectureController {

    @Autowired
    private final RecordLectureService recordLectureService;

    @ForTeacher
    @PostMapping("/api/recorded-lectures")
    public ResponseEntity<RecordLectureDto> create(@RequestBody RecordLectureDto dto, HttpServletRequest request) {

        return TryCatchControllerTemplate.execute(() ->  {

            JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
            String teacherId = payload.getSub();

            log.info("teacherID : " + teacherId);

            return recordLectureService.create(
                    dto.getClassroomId(),
                    dto.getTitle(),
                    dto.getDescription(),
                    dto.getUrl(),
                    dto.getOrderNum(),
                    teacherId
            );
        });
    }

    @ForUser
    @GetMapping("/api/recorded-lectures/lecture/{lectureId}/{studentId}")
    public ResponseEntity<RecordLectureDto> getDetail(@PathVariable Long lectureId, @PathVariable String studentId) {

        return TryCatchControllerTemplate.execute(() -> recordLectureService.getDetail(lectureId, studentId));
    }

    @GetMapping("/api/recorded-lectures/{classroomId}")
    public ResponseEntity<RecordLecturePageResultDto> selectList(@PathVariable String classroomId, int page) {

        return TryCatchControllerTemplate.execute(() -> recordLectureService.selectList(classroomId, page));
    }

    @ForTeacher
    @PatchMapping("/api/recorded-lectures/{lectureId}")
    public ResponseEntity<RecordLectureDto> update(@PathVariable Long lectureId, @RequestBody RecordLectureDto dto,
                                                   HttpServletRequest request) {

        return TryCatchControllerTemplate.execute(() -> {

            JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
            String teacherId = payload.getSub();

            return recordLectureService.update(
                    lectureId,
                    dto.getTitle(),
                    dto.getDescription(),
                    dto.getUrl(),
                    dto.getOrderNum(),
                    teacherId,
                    dto.getClassroomId()
            );
        });
    }

    @ForTeacher
    @DeleteMapping("/api/recorded-lectures/{lectureId}/{classroomId}")
    public ResponseEntity<Object> delete(@PathVariable Long lectureId, @PathVariable String classroomId,
                                         HttpServletRequest request) {

        return TryCatchControllerTemplate.execute(() -> {

            JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
            String teacherId = payload.getSub();

            recordLectureService.delete(lectureId, teacherId, classroomId);
            return null;
        });
    }
}
