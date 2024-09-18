package com.docker.DockerLab.repository;

import com.docker.DockerLab.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
