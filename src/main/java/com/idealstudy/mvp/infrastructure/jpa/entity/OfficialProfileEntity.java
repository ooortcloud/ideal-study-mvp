package com.idealstudy.mvp.infrastructure.jpa.entity;

import com.idealstudy.mvp.application.dto.OfficialProfileDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.TeacherEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "OFFICIAL_PROFILE")
public class OfficialProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OFFICIAL_PROFILE_ID")
    private Long id;

    // HTML 데이터를 저장
    @Column(columnDefinition = "TEXT", nullable = true)
    private String content;
    
    // 프로필 - 강사 일대일 단방향 연관관계
    // 단방향 매핑으로 성능 최적화
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")  // MEMBER의 PK를 관리할 FK로 지목
    private TeacherEntity teacher;

    public OfficialProfileDto toDto() {
        return OfficialProfileDto.builder()
                .content(this.content)
                .build();
    }

    public static OfficialProfileEntity toEntity(OfficialProfileDto dto) {
        return OfficialProfileEntity.builder()
                .content(dto.getContent())
                .build();
    }
}
