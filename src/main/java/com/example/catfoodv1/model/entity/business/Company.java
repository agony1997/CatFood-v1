package com.example.catfoodv1.model.entity.business;

import com.example.catfoodv1.model.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(of = "companyCode", callSuper = false)
@Getter
@Setter
@ToString(exclude = "brands")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company")
public class Company extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String companyCode;

    @NotNull
    private String companyName;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Brand> brands = new ArrayList<>(); // 旗下品牌
}
