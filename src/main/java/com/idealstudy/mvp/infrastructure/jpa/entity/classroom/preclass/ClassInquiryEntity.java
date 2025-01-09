package com.idealstudy.mvp.infrastructure.jpa.entity.classroom.preclass;

import com.idealstudy.mvp.enums.classroom.Visibility;
import com.idealstudy.mvp.infrastructure.jpa.entity.BaseEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "class_inquiry")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ClassInquiryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id")
    private Long id;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private ClassroomEntity classroom;

    @Override
    public String toString() {
        return "ClassInquiryEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", visibility=" + visibility +
                ", classroom=" + classroom +
                '}';
    }
}
