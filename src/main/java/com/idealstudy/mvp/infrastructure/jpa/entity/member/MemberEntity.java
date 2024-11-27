package com.idealstudy.mvp.infrastructure.jpa.entity.member;

import com.idealstudy.mvp.enums.member.*;
import com.idealstudy.mvp.infrastructure.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
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

    @Column(columnDefinition = "TINYINT")
    private int fromSocial;

    @Column(columnDefinition = "TINYINT default 1")
    private int init;

    @Column(columnDefinition = "TINYINT default 0")
    private int deleted;
}
