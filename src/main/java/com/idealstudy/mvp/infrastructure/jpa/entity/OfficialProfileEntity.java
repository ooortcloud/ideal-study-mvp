package com.idealstudy.mvp.infrastructure.jpa.entity;

import com.idealstudy.mvp.application.dto.OfficialProfileDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.TeacherEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "OFFICIAL_PROFILE")
@NoArgsConstructor
@AllArgsConstructor
public class OfficialProfileEntity {

    @Id
    @Column(name = "USER_ID")
    private String id;

    // HTML 데이터를 저장
    // @Column(columnDefinition = "TEXT", nullable = true)
    private String content;
    
    // TEACHER와 일대일 단방향 식별 관계
    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private TeacherEntity teacher;

    public OfficialProfileDto toDto() {
        return OfficialProfileDto.builder()
                .teacherId(this.id)
                .content(this.content)
                .build();
    }

    public static OfficialProfileEntity toEntity(OfficialProfileDto dto) {
        return OfficialProfileEntity.builder()
                .id(dto.getTeacherId())
                .content(dto.getContent())
                .build();
    }
}
