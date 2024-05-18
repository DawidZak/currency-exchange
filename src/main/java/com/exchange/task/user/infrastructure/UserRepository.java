package com.exchange.task.user.infrastructure;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UserRepository extends JpaRepository<UserEntity,String> {

    @Override
    @EntityGraph(attributePaths = {"accounts"})
    Optional<UserEntity> findById(String id);
}
