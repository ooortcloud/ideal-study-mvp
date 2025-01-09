package com.idealstudy.mvp.application.dto.classroom;

import com.idealstudy.mvp.enums.classroom.ClassroomStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClassroomResponseDto {
    private String id;
    private String title;
    private String description;
    private int capacity;
    private String createdBy;
    private String thumbnail;
    private ClassroomStatus status;

    /*
    // 엔티티에서 ResponseDto로 변환
    public static ClassroomResponseDto fromEntity(ClassroomEntity entity) {
        ClassroomResponseDto dto = new ClassroomResponseDto();
        dto.setId(entity.getClassroomId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setCapacity(entity.getCapacity());
        dto.setThumbnail(entity.getThumbnail());
        dto.setCreatedBy(entity.getCreatedBy());
        return dto;
    }

     */
}
