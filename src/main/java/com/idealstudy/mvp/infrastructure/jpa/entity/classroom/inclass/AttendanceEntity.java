package com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass;

import com.idealstudy.mvp.infrastructure.jpa.entity.BaseEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "attendance")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AttendanceEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private ClassroomEntity classroom;

    private LocalDateTime checkOutDate;
}
