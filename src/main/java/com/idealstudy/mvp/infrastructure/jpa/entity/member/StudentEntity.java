package com.idealstudy.mvp.infrastructure.jpa.entity.member;

import com.idealstudy.mvp.enums.member.Grade;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import jakarta.persistence.*;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "Student")
@Table(name = "STUDENT")
@DiscriminatorValue("S")
@SuperBuilder
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "student_id")
public class StudentEntity extends MemberEntity {

    private String school;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    /* EnrollmentEntity로 대체함
    // Classroom과 N:M 관계
    @ManyToMany(mappedBy = "students")
    private List<ClassroomEntity> classrooms; // 참여한 수업 목록

     */

    // @ManyToOne
    // @JoinColumn(name = "parents_id")
    // private ParentsEntity parents;

    public StudentEntity() {
        super();
    }
}
