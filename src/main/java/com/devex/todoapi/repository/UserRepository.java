package com.devex.todoapi.repository;

import com.devex.todoapi.model.User;
import com.devex.todoapi.projection.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(nativeQuery = true, value = """
			SELECT users.email AS username, users.password, roles.id AS roleId, roles.authority
			FROM users
			INNER JOIN user_role ON users.id = user_role.user_id
			INNER JOIN roles ON roles.id = user_role.role_id
			WHERE users.email = :email
		""")
    List<UserDetailsProjection> searchUserAndRolesByEmail(String email);
}
