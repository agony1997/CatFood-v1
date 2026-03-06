package com.example.catfood.domain.business.entity;

import com.example.catfood.domain.common.entity.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(of = "companyCode", callSuper = false)
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company")
public class Company extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "company_code", nullable = false, unique = true)
    private String companyCode;

    @NotNull
    @Column(name = "company_name", nullable = false)
    private String companyName;
}
