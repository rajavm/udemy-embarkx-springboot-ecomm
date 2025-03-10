package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Table(name="users",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @NotBlank
    @Size(max=20)
    @NonNull // to use @RequiredArgsConstructor
    @Column(name="username")
    private String userName;

    @NotBlank
    @Size(max=50)
    @Email
    @NonNull // to use @RequiredArgsConstructor
    @Column(name="email")
    private String email;

    @NotBlank
    @Size(max=120)
    @NonNull // to use @RequiredArgsConstructor
    @Column(name="password")
    private String password;

    //same user can be admin,user,seller and likewise admin,user,seller can be many users
    @ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
                joinColumns = @JoinColumn(name="user_id"),
                inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();

    //Mapping of user address
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "user_address",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="address_id"))
    private List<Address> addresses = new ArrayList<>();

    //This is for SELLER side. Each user of role seller can have multiple products associated with him
    @OneToMany(mappedBy="user",cascade = {CascadeType.PERSIST,CascadeType.MERGE},
                orphanRemoval = true)
    @ToString.Exclude
    private Set<Product> products;

}
