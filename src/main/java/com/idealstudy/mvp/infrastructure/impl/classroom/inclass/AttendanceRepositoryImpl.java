package com.idealstudy.mvp.infrastructure.impl.classroom.inclass;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.AttendanceDto;
import com.idealstudy.mvp.application.repository.inclass.AttendanceRepository;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.AttendanceEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.ClassroomJpaRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.inclass.AttendanceJpaRepository;
import com.idealstudy.mvp.mapstruct.AttendanceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class AttendanceRepositoryImpl implements AttendanceRepository {

    @Autowired
    private final AttendanceJpaRepository attendanceJpaRepository;

    @Autowired
    private final ClassroomJpaRepository classroomJpaRepository;



    @Override
    public AttendanceDto checkIn(String classroomId) {

        ClassroomEntity classroomEntity = classroomJpaRepository.findById(classroomId).orElseThrow();

        AttendanceEntity attendanceEntity = AttendanceEntity.builder()
                .classroom(classroomEntity)
                .build();

        return AttendanceMapper.INSTANCE.toDto(attendanceJpaRepository.save(attendanceEntity));
    }

    @Override
    public AttendanceDto checkOut(Long id, String studentId, LocalDateTime now) {

        AttendanceEntity entity = attendanceJpaRepository.findById(id).orElseThrow();
        entity.setCheckOutDate(now);

        return AttendanceMapper.INSTANCE.toDto(attendanceJpaRepository.save(entity));
    }

    @Override
    public List<AttendanceDto> getIndividualAttendance(String studentId, LocalDate startDate, LocalDate endDate) {

        List<AttendanceEntity> list = attendanceJpaRepository.findAttendanceByMonth(startDate, endDate, studentId,
                Sort.by("regDate", "id").ascending());

        return list.stream().map(AttendanceMapper.INSTANCE::toDto).toList();
    }

    @Override
    public List<AttendanceDto> getIndividualAttendanceInClassroom(String classroomId, LocalDate startDate,
                                                                  LocalDate endDate) {

        List<AttendanceEntity> list = attendanceJpaRepository.findAttendanceByMonthAndClassroomId(startDate, endDate,
                classroomId, Sort.by("regDate", "id").ascending());

        return list.stream().map(AttendanceMapper.INSTANCE::toDto).toList();
    }

    @Override
    public List<AttendanceDto> getTodayClassroomAttendance(String classroomId, LocalDate date) {

        List<AttendanceEntity> list = attendanceJpaRepository.findTodayAttendance(date, classroomId,
                Sort.by("id").ascending());

        return list.stream().map(AttendanceMapper.INSTANCE::toDto).toList();
    }

    @Override
    public AttendanceDto findById(Long id) {

        AttendanceEntity entity = attendanceJpaRepository.findById(id).orElseThrow();

        return AttendanceMapper.INSTANCE.toDto(entity);
    }

    @Override
    public Optional<AttendanceDto> todayVisitInfo(LocalDateTime now, String studentId) {

        Optional<AttendanceEntity> entity = attendanceJpaRepository.findAttendanceByDateAndStudent(now.toLocalDate(),
                studentId, Sort.by("id").ascending());

        if(entity.isEmpty())
            return Optional.empty();

        return Optional.of(AttendanceMapper.INSTANCE.toDto(entity.get()));
    }
}
