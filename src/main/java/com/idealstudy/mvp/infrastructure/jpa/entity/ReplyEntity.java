package com.idealstudy.mvp.infrastructure.jpa.entity;

import java.util.List;

import com.idealstudy.mvp.enums.classroom.Visibility;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.PostEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.preclass.ClassInquiryEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "reply")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String content;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private ReplyEntity parentComment;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne
    @JoinColumn(name = "class_inquiry_id")
    private ClassInquiryEntity classInquiry;

    // Like와 일대다 양방향 연관관계
    @ManyToMany(mappedBy = "replies")
    private List<LikedEntity> likes;

    @Override
    public String toString() {
        return "ReplyEntity{" +
                "commentId=" + commentId +
                ", content='" + content + '\'' +
                ", visibility=" + visibility +
                ", parentComment=" + parentComment +
                ", post=" + post +
                ", classInquiry=" + classInquiry +
                ", likes=" + likes +
                '}';
    }
}