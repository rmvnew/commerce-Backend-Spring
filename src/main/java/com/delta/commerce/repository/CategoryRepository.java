package com.delta.commerce.repository;

import com.delta.commerce.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> , JpaSpecificationExecutor {

    @Query("""
            select c from Category c
            where (:name is null or c.categoryName like concat('%',:name,'%') )
                        """)
    Page<Category> getAllCategory(@Param("name") String name, Pageable pageable);


    @Query("""
            select c from Category c
            where 
            c.categoryName = :name
            """)
    Optional<Category> findByName(@Param("name") String name);

}
