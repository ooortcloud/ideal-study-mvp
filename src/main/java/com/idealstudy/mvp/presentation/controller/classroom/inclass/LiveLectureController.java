package com.idealstudy.mvp.presentation.controller.classroom.inclass;

import com.idealstudy.mvp.application.dto.classroom.inclass.LiveLectureDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.LiveLecturePageResultDto;
import com.idealstudy.mvp.application.service.classroom.inclass.LiveLectureService;
import com.idealstudy.mvp.security.annotation.ForTeacher;
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
public class LiveLectureController {

    @Autowired
    private final LiveLectureService liveLectureService;

    @ForTeacher
    @PostMapping("/api/live-lectures")
    public ResponseEntity<LiveLectureDto> create(@RequestBody LiveLectureDto dto, HttpServletRequest request) {

        return TryCatchControllerTemplate.execute(() -> {

            JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
            String teacherId = payload.getSub();
            String classroomId = dto.getClassroomId();

            return liveLectureService.create(classroomId, dto.getTitle(), dto.getStartTime(), dto.getEndTime(),
                    dto.getStudySpace(), dto.getPlatform(), teacherId);
        });
    }

    @GetMapping("/live-lectures/{lectureId}")
    public ResponseEntity<LiveLectureDto> getOne(@PathVariable Long lectureId) {

        return TryCatchControllerTemplate.execute(() -> liveLectureService.getOne(lectureId));

    }

    @GetMapping("/live-lectures/list/{classroomId}")
    public ResponseEntity<LiveLecturePageResultDto> getList(@PathVariable String classroomId) {

        return TryCatchControllerTemplate.execute(() -> liveLectureService.getList(classroomId.trim()));
    }

    @ForTeacher
    @PatchMapping("/api/live-lectures")
    public ResponseEntity<LiveLectureDto> update(@RequestBody LiveLectureDto dto, HttpServletRequest request) {

        return TryCatchControllerTemplate.execute(() -> {

            JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
            String teacherId = payload.getSub();
            String classroomId = dto.getClassroomId();

            return liveLectureService.update(
                    dto.getId(),
                    dto.getTitle(),
                    dto.getStartTime(),
                    dto.getEndTime(),
                    dto.getStudySpace(),
                    dto.getPlatform(),
                    teacherId,
                    classroomId
            );
        });
    }

    @ForTeacher
    @DeleteMapping("/api/live-lectures")
    public ResponseEntity<Object> delete(@RequestBody LiveLectureDto dto, HttpServletRequest request) {

        return TryCatchControllerTemplate.execute(() -> {

            JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
            String teacherId = payload.getSub();
            String classroomId = dto.getClassroomId();

            liveLectureService.delete(dto.getId(), teacherId, classroomId);
            return null;
        });
    }
}
