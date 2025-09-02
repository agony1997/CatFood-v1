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
    @Column(name = "create_dt", nullable = false, updatable = false)
    private LocalDateTime createDT;

    @LastModifiedDate
    @Column(name = "update_dt", nullable = false)
    private LocalDateTime updateDT;

    @CreatedBy
    @Column(name = "creator", nullable = false, updatable = false)
    private String creator;

    @LastModifiedBy
    @Column(name = "updater", nullable = false)
    private String updater;

}