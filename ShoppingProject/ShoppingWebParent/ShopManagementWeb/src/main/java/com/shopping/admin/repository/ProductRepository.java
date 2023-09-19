package com.shopping.admin.repository;

import com.shopping.common.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% AND (p.brand.id = ?2 OR ?2 = 0)")
    Page<Product> listByPageIfCategoryIdNull(Pageable pageable, String nameSearch, Integer brandIdSearch);
    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% AND p.category.allParentIDs LIKE %?2% AND (p.brand.id = ?3 OR ?3 = 0)")
    Page<Product> listByPageIfCategoryIdNotNull(Pageable pageable, String nameSearch, String categoryIdMatch, Integer brandIdSearch);

}
