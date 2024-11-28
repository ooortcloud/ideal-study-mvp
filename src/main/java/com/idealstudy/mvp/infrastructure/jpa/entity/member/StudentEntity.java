package com.idealstudy.mvp.infrastructure.jpa.entity.member;

import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import java.util.List;

import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "Student")
@Table(name = "STUDENT")
@DiscriminatorValue("S")
public class StudentEntity extends MemberEntity {

    // Classroom과 N:M 관계
    @ManyToMany(mappedBy = "students")
    private List<ClassroomEntity> classrooms; // 참여한 수업 목록

}
