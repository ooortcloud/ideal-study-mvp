package com.idealstudy.mvp.application.repository.inclass;

import com.idealstudy.mvp.application.dto.classroom.inclass.AttendanceDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository {

    AttendanceDto checkIn(String classroomId);

    AttendanceDto checkOut(Long id, String studentId, LocalDateTime now);

    List<AttendanceDto> getIndividualAttendance(String studentId, LocalDate startDate, LocalDate endDate);

    List<AttendanceDto> getIndividualAttendanceInClassroom(String classroomId, LocalDate startDate, LocalDate endDate);

    List<AttendanceDto> getTodayClassroomAttendance(String classroomId, LocalDate date);

    AttendanceDto findById(Long id);

    Optional<AttendanceDto> todayVisitInfo(LocalDateTime now, String studentId);
}
