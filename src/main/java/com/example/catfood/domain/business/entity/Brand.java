package com.example.catfood.domain.business.entity;

import com.example.catfood.domain.common.entity.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(of = "brandCode", callSuper = false)
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "brand")
public class Brand extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "brand_code", nullable = false, unique = true)
    private String brandCode;

    @NotNull
    @Column(name = "brand_name", nullable = false)
    private String brandName;

    @Column(name = "company_id")
    private UUID companyId;
}
