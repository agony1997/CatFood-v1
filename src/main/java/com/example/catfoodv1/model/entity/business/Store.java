package com.example.catfoodv1.model.entity.business;

import com.example.catfoodv1.model.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(of = "storeCode", callSuper = false)
@Data
@Entity
@Builder
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
    @Column(name = "store_name")
    private String storeName;

    @Column(name = "website_url")
    private String websiteUrl;

    public Store(UUID id, String storeCode, String storeName) {
        this.id = id;
        this.storeCode = storeCode;
        this.storeName = storeName;
    }
}