package com.example.catfoodv1.model.entity.auth;

import com.example.catfoodv1.model.entity.product.ProductReview;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email; // 登入用Email，唯一

    @Column(nullable = false)
    private String username; // 顯示用名稱，可以不唯一

    @Column(nullable = false)
    private String password; // 密碼

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>(); // 使用者角色

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductReview> reviews; // 使用者發布的評論
}
