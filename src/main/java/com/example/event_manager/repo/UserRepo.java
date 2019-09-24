package com.example.event_manager.repo;

import com.example.event_manager.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

  Optional<User> findByLogin(String login);

  boolean existsByLogin(String login);
}
