package com.example.simpleProject.repository;

import com.example.simpleProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
//The interface was created to communicate with the database using Hibernate and JPA
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
    public Boolean existsByEmail(String email);

}
