package com.example.catfood.domain.business.entity;

import com.example.catfood.domain.common.entity.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(of = "storeCode", callSuper = false)
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "store")
public class Store extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "store_code", nullable = false, unique = true)
    private String storeCode;

    @NotNull
    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Column(name = "website_url")
    private String websiteUrl;
}
