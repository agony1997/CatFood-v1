package com.example.catfoodv1.model.entity.product;

import com.example.catfoodv1.model.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(of = "tagCode", callSuper = false)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tag")
public class Tag extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String tagCode;

    @NotNull
    @Column(nullable = false)
    private String tagName;

    @ManyToMany(mappedBy = "tags")
    private List<Product> products = new ArrayList<>(); // 關聯的產品
}