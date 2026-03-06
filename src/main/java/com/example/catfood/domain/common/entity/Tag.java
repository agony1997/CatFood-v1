package com.example.catfood.domain.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(of = "tagCode", callSuper = false)
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tag")
public class Tag extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "tag_code", unique = true, nullable = false)
    private String tagCode;

    @NotNull
    @Column(name = "tag_name", nullable = false)
    private String tagName;
}
