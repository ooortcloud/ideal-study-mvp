package com.idealstudy.mvp.infrastructure.jpa.entity.classroom.preclass;

import com.idealstudy.mvp.infrastructure.jpa.entity.BaseEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "FAQ")
@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
public class FAQEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faq_id")
    private Long id;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private ClassroomEntity classroom;

    public FAQEntity() {

    }
}
