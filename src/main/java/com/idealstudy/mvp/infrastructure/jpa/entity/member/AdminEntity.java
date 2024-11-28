package com.idealstudy.mvp.infrastructure.jpa.entity.member;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "Admin")
@Table(name = "ADMIN")
@DiscriminatorValue("A")
public class AdminEntity extends MemberEntity{

    // nothing
}
