package com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass;

import com.idealstudy.mvp.enums.classroom.Platform;
import com.idealstudy.mvp.infrastructure.jpa.entity.BaseEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "live_lecture")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LiveLectureEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private ClassroomEntity classroom;

    private String title;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String studySpace;

    @Enumerated(value = EnumType.STRING)
    private Platform platform;
}
