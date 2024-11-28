package com.idealstudy.mvp.infrastructure.jpa.entity.member;

import com.idealstudy.mvp.enums.member.*;
import com.idealstudy.mvp.infrastructure.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Member")
@Table(name = "MEMBER")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="DTYPE") // 부모클래스 구분 칼럼
@Getter
@Setter  // for Writer accessor
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "char(36)")
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column
    private String name;

    @Column(length = 15, nullable = true)
    private String phoneAddress;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender sex;

    @Column(columnDefinition = "char(36)")
    private String referralId;

    @Column(columnDefinition = "INT default 0")
    private Integer level;

    @Enumerated(EnumType.STRING)
    private Role role;

    /* DB 원자성까지 엄격히 고려하는 경우.
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Role> roleSet = new HashSet<>();

    public void addRole(Role role) {
        roleSet.add(role);
    }
     */

    @Column(columnDefinition = "varchar(255) default '안녕하세요. 잘 부탁드립니다.'")
    private String introduction;

    // Specifies that a persistent property or field should be persisted as a large object
    // to a database-supported large object type.
    @Lob @Basic(fetch=FetchType.LAZY)
    // LONGBLOB: MySQL에서 대용량 바이너리 데이터를 저장하기 위한 데이터 타입을 지정
    @Column(name = "image_data", columnDefinition="LONGBLOB")
    private byte[] profile;

    @Column(columnDefinition = "TINYINT")
    private int fromSocial;

    @Column(columnDefinition = "TINYINT default 1")
    private int init;

    @Column(columnDefinition = "TINYINT default 0")
    private int deleted;
}
