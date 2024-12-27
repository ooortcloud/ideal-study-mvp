package com.idealstudy.mvp.domain;

import com.idealstudy.mvp.application.dto.classroom.inclass.exam.ExamListResponseDto;
import com.idealstudy.mvp.enums.classroom.AssessmentStatus;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class Assessment {

    public void setAssessmentStatus(List<ExamListResponseDto> list) {

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        for(ExamListResponseDto dto : list ) {
            if(now.isBefore(dto.getStartTime()))
                dto.setStatus(AssessmentStatus.SCHEDULED);

            if(now.isAfter(dto.getStartTime()) && now.isBefore(dto.getEndTime()))
                dto.setStatus(AssessmentStatus.IN_PROGRESS);

            if(now.isAfter(dto.getEndTime()))
                dto.setStatus(AssessmentStatus.COMPLETED);
        }
    }
}
