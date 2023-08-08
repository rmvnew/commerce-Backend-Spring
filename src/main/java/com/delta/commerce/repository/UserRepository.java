package com.delta.commerce.repository;

import com.delta.commerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByUserEmail(String userEmail);

    Optional<User> findUserByUserCompleteName(String name);

    Optional<User> findUserByUserEmailAndRecoverCode(String userEmail,String code);

    @Query("""
            select u from User u
            where 
            (:name is null or u.userCompleteName like concat('%',:name,'%') ) AND 
            u.isActive = true
            """)
    Page<User> getAllUsers(String name, Pageable pageable);





}
