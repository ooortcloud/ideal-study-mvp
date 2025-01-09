package com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass;

import com.idealstudy.mvp.enums.classroom.MaterialsStatus;
import com.idealstudy.mvp.infrastructure.jpa.entity.BaseEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.StudentEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "materials")
@Getter
@Setter
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MaterialsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private ClassroomEntity classroom;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    private Integer orderNum;

    @Enumerated(value = EnumType.STRING)
    private MaterialsStatus status;

    private String title;

    private String description;

    private String materialUri;
}
