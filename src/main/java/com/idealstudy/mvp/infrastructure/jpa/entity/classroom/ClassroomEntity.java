package com.idealstudy.mvp.infrastructure.jpa.entity.classroom;

import com.idealstudy.mvp.enums.classroom.ClassroomStatus;
import com.idealstudy.mvp.infrastructure.jpa.entity.BaseEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.LikedEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.StudentEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.TeacherEntity;
import jakarta.persistence.*;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "classroom")
@SuperBuilder
@Getter
@Setter
public class ClassroomEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "char(36)")
    private String classroomId; // 수업 ID

    private String title; // 수업명

    private String description; // 수업소개

    private Integer capacity; // 모집인원

    private String thumbnail; // 썸네일 이미지 주소

    @Enumerated(EnumType.STRING)
    private ClassroomStatus status;

    // Teacher와 1:N 관계
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private TeacherEntity teacher; // 강사ID (수업을 운영)

    // Student과 N:M 관계 (중간 테이블 설정)
    @ManyToMany
    @JoinTable(
            name = "classroom_student",
            joinColumns = @JoinColumn(name = "classroom_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<StudentEntity> students; // 학생ID (수업에 참가)

    @ManyToMany(mappedBy = "classrooms")
    private List<LikedEntity> likes;

    public ClassroomEntity() {

    }

    @Override
    public String toString() {
        return "ClassroomEntity{" +
                "classroomId='" + classroomId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", capacity=" + capacity +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
