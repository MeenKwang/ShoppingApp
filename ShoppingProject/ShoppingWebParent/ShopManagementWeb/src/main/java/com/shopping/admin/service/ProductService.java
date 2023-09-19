package com.shopping.admin.service;

import com.shopping.admin.dto.ProductFormDto;
import com.shopping.admin.dto.ProductListDto;
import com.shopping.common.entity.Product;
import com.shopping.common.exception.ProductNotFoundException;
import org.springframework.data.domain.Page;

public interface ProductService {
    public Page<ProductListDto> listByPage(Integer pageNum, Integer categoryId, Integer brandId, String nameSearch, Integer pageSize,
                                           String sortField, String sortDir);
    public Product save(Product product);
    public void deleteProduct(Integer id) throws ProductNotFoundException;

    ProductFormDto getProductById(Integer id) throws ProductNotFoundException;
}
