package com.ecommerce.project.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private Integer roleId;

    @Enumerated(EnumType.STRING)//Enums are persisted in DB as integer.but i want it to persist as string
    @Column(length=20,name="role_name")
    @ToString.Exclude
    private AppRole roleName;


}
