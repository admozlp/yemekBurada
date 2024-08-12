package com.ademozalp.yemek_burada.repository;

import com.ademozalp.yemek_burada.model.Food;
import com.ademozalp.yemek_burada.model.enums.FoodStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long>, JpaSpecificationExecutor<Food> {
    Optional<Food> findByIdAndStatus(Long id, FoodStatus status);
}
