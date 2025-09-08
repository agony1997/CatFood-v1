package com.example.catfoodv1.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

    @CreatedDate
    @Column(name = "create_dt", updatable = false)
    private LocalDateTime createDT;

    @LastModifiedDate
    @Column(name = "update_dt")
    private LocalDateTime updateDT;

    @CreatedBy
    @Column(name = "creator", updatable = false)
    private String creator;

    @LastModifiedBy
    @Column(name = "updater")
    private String updater;

}