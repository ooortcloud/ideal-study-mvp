package com.idealstudy.mvp.infrastructure.jpa.repository.classroom.inclass;

import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.AttendanceEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceJpaRepository extends JpaRepository<AttendanceEntity, Long> {

    @Query("SELECT a FROM AttendanceEntity a WHERE DATE(a.regDate) BETWEEN :startDate AND :endDate " +
            "AND a.createdBy = :studentId")
    List<AttendanceEntity> findAttendanceByMonth(@Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate,
                                                 @Param("studentId") String studentId,
                                                 Sort sort);

    @Query("SELECT a FROM AttendanceEntity a WHERE DATE(a.regDate) BETWEEN :startDate AND :endDate " +
            "AND a.classroom.classroomId = :classroomId")
    List<AttendanceEntity> findAttendanceByMonthAndClassroomId(@Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate,
                                                 @Param("classroomId") String classroomId,
                                                 Sort sort);

    @Query("SELECT a FROM AttendanceEntity a WHERE DATE(a.regDate) = :date AND a.createdBy = :studentId")
    Optional<AttendanceEntity> findAttendanceByDateAndStudent(@Param("date") LocalDate date,
                                                              @Param("studentId") String studentId,
                                                              Sort sort);


    @Query("SELECT a FROM AttendanceEntity a WHERE DATE(a.regDate) = :date AND a.classroom.classroomId = :classroomId")
    List<AttendanceEntity> findTodayAttendance(@Param("date") LocalDate date,
                                               @Param("classroomId") String classroomId,
                                               Sort sort);
}
