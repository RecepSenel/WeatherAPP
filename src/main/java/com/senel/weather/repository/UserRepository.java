package com.senel.weather.repository;

import com.senel.weather.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {
    public Optional<User> findByUsername(String name);

}
