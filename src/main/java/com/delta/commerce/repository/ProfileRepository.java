package com.delta.commerce.repository;

import com.delta.commerce.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile,Long>, JpaSpecificationExecutor {

    Optional<Profile> findProfileByProfileName(@Param("profileName") String profileName);

    @Query("""
            select p from Profile p
            where (:name is null or p.profileName like concat('%',:name,'%'))
                    """)
    Page<Profile> getAllProfiles(@Param("name") String name, Pageable page);

}
