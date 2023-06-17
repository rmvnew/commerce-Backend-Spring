package com.delta.commerce.repository;

import com.delta.commerce.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile,Long> {

    Optional<Profile> findProfileByProfileName(@Param("profileName") String profileName);

}
