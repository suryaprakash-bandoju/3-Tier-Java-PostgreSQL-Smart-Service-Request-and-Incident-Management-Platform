package com.example.servicerequest.repository;
import com.example.servicerequest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}