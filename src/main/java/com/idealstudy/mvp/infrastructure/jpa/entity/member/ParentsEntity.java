package com.idealstudy.mvp.infrastructure.jpa.entity.member;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;


@Entity(name = "Parents")
@Table(name = "PARENTS")
@DiscriminatorValue("P")
@SuperBuilder
@Getter
@Setter
public class ParentsEntity extends MemberEntity {

    // PARENTS - STUDENT 다대다 단방향 비식별 관계
    @ManyToMany
    @JoinTable( name = "PARENTS_STUDENT",
            joinColumns = @JoinColumn(name = "PARENTS_ID", referencedColumnName = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "STUDENT_ID", referencedColumnName = "student_id")
    )
    private List<StudentEntity> students;

    public ParentsEntity() {

    }
}
