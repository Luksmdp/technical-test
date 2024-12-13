package com.technicaltest.ecommerce.repository;


import com.technicaltest.ecommerce.model.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    @Query(value = """
    SELECT * FROM price p
    WHERE p.brand_id = :brandId
      AND p.product_id = :productId
      AND :applicationDate BETWEEN p.start_date AND p.end_date
    ORDER BY p.priority DESC
    LIMIT 1
    """, nativeQuery = true)
    Optional<Price> findTopByApplicablePrice(@Param("brandId") Long brandId,
                                             @Param("productId") Long productId,
                                             @Param("applicationDate") LocalDateTime applicationDate);
}

