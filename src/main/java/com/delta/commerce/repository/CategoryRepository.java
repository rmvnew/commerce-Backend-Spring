package com.delta.commerce.repository;

import com.delta.commerce.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("""
            select c from Category c
            where (:name is null or c.categoryName like concat('%',:name,'%') )
                        """)
    Page<Category> getAllCategory(@Param("name") String name, Pageable pageable);

}
