package com.example.catfoodv1.model.entity.business;

import com.example.catfoodv1.model.Auditable;
import com.example.catfoodv1.model.entity.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(of = "brandCode", callSuper = false)
@Getter
@Setter
@ToString(exclude = {"company", "products"})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "brand")
public class Brand extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String brandCode;

    @NotNull
    private String brandName;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company; // 所屬公司

    @OneToMany(mappedBy = "brand")
    private List<Product> products = new ArrayList<>(); // 旗下產品

}
