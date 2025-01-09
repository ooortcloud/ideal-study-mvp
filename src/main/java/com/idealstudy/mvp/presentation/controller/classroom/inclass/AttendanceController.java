package com.idealstudy.mvp.presentation.controller.classroom.inclass;

import com.idealstudy.mvp.application.dto.classroom.inclass.AttendanceDto;
import com.idealstudy.mvp.application.service.classroom.inclass.AttendanceService;
import com.idealstudy.mvp.presentation.controller.component.TokenComponent;
import com.idealstudy.mvp.security.annotation.ForStudent;
import com.idealstudy.mvp.security.annotation.ForTeacher;
import com.idealstudy.mvp.util.TryCatchControllerTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AttendanceController {

    @Autowired
    private final AttendanceService attendanceService;

    @Autowired
    private final TokenComponent tokenComponent;

    @ForStudent
    @PostMapping("/api/attendance/check-in/{classroomId}")
    public ResponseEntity<AttendanceDto> checkIn(@PathVariable String classroomId) {

        return TryCatchControllerTemplate.execute(() ->
                attendanceService.checkIn(classroomId, tokenComponent.getUserId()));
    }

    @ForStudent
    @PostMapping("/api/attendance/check-out/{id}")
    public ResponseEntity<AttendanceDto> checkOut(@PathVariable Long id) {

        return TryCatchControllerTemplate.execute(() ->
                attendanceService.checkOut(id, tokenComponent.getUserId()));
    }

    @GetMapping("/attendance/student/{studentId}")
    public ResponseEntity<List<AttendanceDto>> getIndividualAttendance(@PathVariable String studentId,
                           @RequestParam int year, @RequestParam int month) {

        return TryCatchControllerTemplate.execute(() ->
            attendanceService.getIndividualAttendance(studentId, year, month)
        );
    }

    @ForTeacher
    @GetMapping("/api/attendance/teacher/{classroomId}")
    public ResponseEntity<List<AttendanceDto>> getIndividualAttendanceInClassroom(@PathVariable String classroomId,
                          @RequestParam int year, @RequestParam int month) {

        return TryCatchControllerTemplate.execute(() ->
                attendanceService.getIndividualAttendanceInClassroom(tokenComponent.getUserId(),
                        classroomId, year, month)
        );
    }

    @ForTeacher
    @GetMapping("/api/attendance/{classroomId}/{date}")
    public ResponseEntity<List<AttendanceDto>> getTodayClassroomAttendance(@PathVariable String classroomId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return TryCatchControllerTemplate.execute(() -> attendanceService.getTodayClassroomAttendance(
                classroomId, date, tokenComponent.getUserId()
        ));
    }
}
