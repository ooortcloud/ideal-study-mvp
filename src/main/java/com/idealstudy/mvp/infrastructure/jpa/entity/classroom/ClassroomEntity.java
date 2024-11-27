package com.idealstudy.mvp.infrastructure.jpa.entity.classroom;

import com.idealstudy.mvp.infrastructure.jpa.entity.BaseEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.StudentEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.TeacherEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "classroom")
public class ClassroomEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "char(36)")
    private String classroomId; // 수업 ID

    private String title; // 수업명

    private String description; // 수업소개

    private Integer capacity; // 모집인원

    private String thumbnail; // 썸네일 이미지 주소

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

    // 아래 필드는 임시 필드로만 추가 TODO : 연관관계 맺어 주어야함

    private String faq; // F&Q 게시판 관련

    private String posts; // 수업 포스트 관련

    private String likes; // 좋아요 관련

    private String inquiries; // 수업 문의 관련

    private String enrollments; // 수업 신청 관련

    private String scheduler; // 스케쥴러 관련

}
