package com.idealstudy.mvp.presentation.dto.classroom;

import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import lombok.Data;

@Data
public class ClassroomRequestDto {
    private String title;
    private String description;
    private int capacity;
    private String thumbnail;
    private String teacherId;

    // 엔티티 생성 메서드
    public ClassroomEntity toEntity() { // TeacherEntity를 파라미터로 받음
        ClassroomEntity entity = new ClassroomEntity();
        entity.setTitle(this.title);
        entity.setDescription(this.description);
        entity.setCapacity(this.capacity);
        entity.setThumbnail(this.thumbnail);
        return entity;
    }

    // 엔티티 업데이트 메서드
    public void updateEntity(ClassroomEntity entity) {
        entity.setTitle(this.title);
        entity.setDescription(this.description);
        entity.setCapacity(this.capacity);
        entity.setThumbnail(this.thumbnail);
    }
}
