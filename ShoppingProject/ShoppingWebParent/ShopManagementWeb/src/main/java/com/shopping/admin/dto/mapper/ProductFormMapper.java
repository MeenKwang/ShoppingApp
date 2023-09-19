package com.shopping.admin.dto.mapper;

import com.shopping.admin.dto.ProductFormDto;
import com.shopping.common.entity.Product;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategorySelectMapper.class, BrandSelectMapper.class, ProductDetailDtoMapper.class,
            ProductExtraImageDtoMapper.class})
public interface ProductFormMapper {
    @Mapping(target = "images", source = "extraImages")
    @Mapping(target = "details", source = "productDetails")
    Product productFormDtoToProduct(ProductFormDto productFormDto);
    @Mapping(target = "extraImages", source = "images")
    @Mapping(target = "productDetails", source = "details")
    ProductFormDto productToProductFormDto(Product product);
}
