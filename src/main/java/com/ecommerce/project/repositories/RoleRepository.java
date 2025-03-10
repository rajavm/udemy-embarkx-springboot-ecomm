package com.ecommerce.project.repositories;

import com.ecommerce.project.model.AppRole;
import com.ecommerce.project.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    //JPA will automatically creates its query based on this function
    //Note: roleName is a field in role, so jpa understands it
    Optional<Role> findByRoleName(AppRole appRole);
}
