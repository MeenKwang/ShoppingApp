package com.shopping.admin.service.impl;

import com.shopping.admin.dto.ProductFormDto;
import com.shopping.admin.dto.ProductListDto;
import com.shopping.admin.dto.mapper.ProductFormMapper;
import com.shopping.admin.dto.mapper.ProductListMapper;
import com.shopping.admin.repository.ProductRepository;
import com.shopping.admin.service.ProductService;
import com.shopping.common.entity.Product;
import com.shopping.common.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.NoSuchElementException;

@Service
// @Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductListMapper productListMapper;
    private final ProductFormMapper productFormMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductListMapper productListMapper
            , ProductFormMapper productFormMapper) {
        this.productRepository = productRepository;
        this.productListMapper = productListMapper;
        this.productFormMapper = productFormMapper;
    }

    @Override
    public Page<ProductListDto> listByPage(Integer pageNum, Integer categoryId, Integer brandId, String nameSearch, Integer pageSize,
                                           String sortField, String sortDir) {
        Sort sort = Sort.by(sortField);
        if(sortDir.equals("asc")) {
            sort = sort.ascending();
        } else {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<Product> productPage = null;
        if(categoryId != 0) {
            String categoryIdMatch = "-" + categoryId + "-";
            productPage = productRepository.listByPageIfCategoryIdNotNull(pageable, nameSearch, categoryIdMatch, brandId);
        } else {
            productPage = productRepository.listByPageIfCategoryIdNull(pageable, nameSearch, brandId);
        }
        Page<ProductListDto> productListDtoPage = productPage.map(product -> productListMapper.productToProductListDto(product));
        return productListDtoPage;
    }

    @Override
    public Product save(Product product) {
        if(product.getId() == null) {
            product.setCreatedTime(new Date());
        }
        product.setAlias(product.getAlias().replaceAll(" ", "-"));
        product.setUpdatedTime(new Date());
        return productRepository.save(product);
    }
    @Override
    public void deleteProduct(Integer id) throws ProductNotFoundException {
        boolean exists = productRepository.existsById(id);

        if(!exists) {
            throw new ProductNotFoundException("Could not find any product with ID " + id);
        }

        productRepository.deleteById(id);
    }

    @Override
    public ProductFormDto getProductById(Integer id) throws ProductNotFoundException{
        try {
            Product product = productRepository.findById(id).get();
            return productFormMapper.productToProductFormDto(product);
        } catch (NoSuchElementException e) {
            throw new ProductNotFoundException("Could not find any product with the ID: " + id);
        }

    }
}
