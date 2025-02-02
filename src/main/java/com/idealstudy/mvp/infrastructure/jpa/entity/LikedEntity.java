package com.idealstudy.mvp.infrastructure.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "liked")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LikedEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likedId;

    /// 선택적 비식별관계
    @ManyToOne
    @JoinColumn(name = "classroom_id", nullable = true)
    private ClassroomEntity classroom;

    /// 선택적 비식별관계
    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = true)
    private ReplyEntity reply;

    // @ManyToMany를 사용하지 말자. 개발 생산성이 오히려 떨어진다.
    /*
    @ManyToMany
    @JoinTable(
        name = "liked_reply",
        joinColumns = @JoinColumn(name = "liked_id"),
        inverseJoinColumns = @JoinColumn(name = "comment_id")
    )
    private List<ReplyEntity> replies = new ArrayList<ReplyEntity>();

    @ManyToMany
    @JoinTable(
            name = "liked_classroom",
            joinColumns = @JoinColumn(name = "liked_id"),
            inverseJoinColumns = @JoinColumn(name = "classroom_id")
    )
    private List<ClassroomEntity> classrooms = new ArrayList<ClassroomEntity>();

    public void addReply(ReplyEntity reply) {

        // 영문을 모르겠지만 초기에 this.replies가 항상 null이어서 이 분기문을 추가해주었다.
        if (this.replies == null) {
            this.replies = new ArrayList<>();
        }

        this.replies.add(reply);
        reply.getLikes().add(this);
    }

    public void addClassroom(ClassroomEntity classroom) {

        // 영문을 모르겠지만 초기에 this.classrooms 항상 null이어서 이 분기문을 추가해주었다.
        if (this.classrooms == null) {
            this.classrooms = new ArrayList<>();
        }

        // Liked 엔티티의 Classroom 엔티티 리스트에 직접 read 정보를 추가해준다.
        this.classrooms.add(classroom);
        // Classroom 엔티티의 Liked 엔티티 리스트에 직접 read 정보를 추가해준다.
        classroom.getLikes().add(this);
    }

     */
}
