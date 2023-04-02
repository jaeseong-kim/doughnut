package com.bread.domain.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Long,User> {

  Optional<User> findByEmail(String email);
}
