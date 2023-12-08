package com.myblog8.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myblog8.Entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String name);
}
