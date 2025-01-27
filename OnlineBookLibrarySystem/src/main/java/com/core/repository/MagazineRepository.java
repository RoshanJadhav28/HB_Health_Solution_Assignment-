package com.core.repository;

import com.core.model.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MagazineRepository extends JpaRepository<Magazine, Long> {
    Optional<Magazine> findByTitle(String title);
}
