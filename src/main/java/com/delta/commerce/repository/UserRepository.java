package com.delta.commerce.repository;

import com.delta.commerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByUserEmail(String userEmail);

    Optional<User> findUserByUserCompleteName(String name);

    Optional<User> findUserByUserEmailAndRecoverCode(String userEmail,String code);

    Page<User> findUsersByUserCompleteNameContainingIgnoreCase(String name, Pageable pageable);

}
