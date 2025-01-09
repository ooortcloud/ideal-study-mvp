package com.idealstudy.mvp.presentation.dto.classroom;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClassroomRequestDto {
    private String title;
    private String description;
    private int capacity;

    /*
    // 엔티티 생성 메서드
    public ClassroomEntity toEntity() { // TeacherEntity를 파라미터로 받음
        ClassroomEntity entity = ClassroomEntity.builder()
                        .title(this.title)
                        .description(this.description)
                        .capacity(this.capacity)
                        .thumbnail(this.thumbnail)
                .build();

        return entity;
    }

    // 엔티티 업데이트 메서드
    public void updateEntity(ClassroomEntity entity) {
        entity.setTitle(this.title);
        entity.setDescription(this.description);
        entity.setCapacity(this.capacity);
        entity.setThumbnail(this.thumbnail);
    }

     */
}
