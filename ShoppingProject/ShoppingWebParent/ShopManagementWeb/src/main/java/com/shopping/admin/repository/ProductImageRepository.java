package com.shopping.admin.repository;

import com.shopping.common.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    Optional<ProductImage> findFirstByOrderByIdAsc();

    Set<ProductImage> findAllByProductId(Integer id);
}
