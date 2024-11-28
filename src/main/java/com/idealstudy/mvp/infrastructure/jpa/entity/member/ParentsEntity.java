package com.idealstudy.mvp.infrastructure.jpa.entity.member;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "Parents")
@Table(name = "PARENTS")
@DiscriminatorValue("P")
public class ParentsEntity extends MemberEntity {

    // nothing
}
