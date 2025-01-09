package com.idealstudy.mvp.infrastructure.jpa.entity.member;

import com.idealstudy.mvp.enums.member.SchoolRegister;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import jakarta.persistence.*;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity(name = "Teacher")
@Table(name = "TEACHER")
@DiscriminatorValue("T")
@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
@PrimaryKeyJoinColumn(name = "teacher_id")
public class TeacherEntity extends MemberEntity {

    // @Column(length = 50)
    private String univ;

    @Enumerated(EnumType.STRING)
    private SchoolRegister status;

    // @Column(length = 30)
    private String subject;

    // Classroom과 1:N 관계
    @OneToMany(mappedBy = "teacher")
    private List<ClassroomEntity> classrooms; // 참여한 클래스 목록


    public TeacherEntity() {

    }
}
