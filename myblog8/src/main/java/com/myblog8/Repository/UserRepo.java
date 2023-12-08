package com.myblog8.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.myblog8.Entity.User;

public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	Optional<User> findByUsernameOrEmail(String username, String email);

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

}
