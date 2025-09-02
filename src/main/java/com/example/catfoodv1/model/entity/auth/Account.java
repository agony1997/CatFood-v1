package com.example.catfoodv1.model.entity.auth;

import com.example.catfoodv1.model.Auditable;
import com.example.catfoodv1.model.entity.product.ProductReview;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(of = "accountCode", callSuper = false)
@Getter
@Setter
@ToString(exclude = {"roles", "reviews"})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String accountCode; // 登入用帳號，唯一

    @NotNull
    @Column(nullable = false)
    private String email; // Email

    @NotNull
    @Column(nullable = false)
    private String displayName; // 顯示用名稱

    @NotNull
    @Column(nullable = false)
    private String password; // 密碼

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>(); // 使用者角色

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductReview> reviews; // 使用者發布的評論
}
