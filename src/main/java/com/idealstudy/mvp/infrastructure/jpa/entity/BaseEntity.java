package com.idealstudy.mvp.infrastructure.jpa.entity;


import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value= AuditingEntityListener.class)
@Getter
public class BaseEntity {

    @CreatedDate
    @Column(name = "reg_date", updatable = false)
    private LocalDateTime regDate; // 생성 일시

    @LastModifiedDate
    @Column(name = "mod_date")
    private LocalDateTime modDate; // 수정 일시

    @Column(name = "del_date")
    private LocalDateTime delDate; // 삭제 일시

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy; // 생성자

    @LastModifiedBy
    @Column(name = "modified_by")
    private String modifiedBy; // 수정자

    @Column(name = "deleted_by")
    private String deletedBy; // 삭제자

}
