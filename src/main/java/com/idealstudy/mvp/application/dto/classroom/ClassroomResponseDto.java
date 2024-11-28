package com.idealstudy.mvp.application.dto.classroom;

import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import lombok.Data;

@Data
public class ClassroomResponseDto {
    private String id;
    private String title;
    private String description;
    private int capacity;
//    private String teacherId;
    private String thumbnail;

    // 엔티티에서 ResponseDto로 변환
    public static ClassroomResponseDto fromEntity(ClassroomEntity entity) {
        ClassroomResponseDto dto = new ClassroomResponseDto();
        dto.setId(entity.getClassroomId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setCapacity(entity.getCapacity());
        dto.setThumbnail(entity.getThumbnail());
        return dto;
    }
}
