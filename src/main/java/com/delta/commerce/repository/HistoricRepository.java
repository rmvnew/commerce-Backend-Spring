package com.delta.commerce.repository;

import com.delta.commerce.entity.Historic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricRepository extends JpaRepository<Historic, Long> {
}
