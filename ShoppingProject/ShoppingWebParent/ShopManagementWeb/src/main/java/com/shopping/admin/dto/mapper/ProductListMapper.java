package com.shopping.admin.dto.mapper;

import com.shopping.admin.dto.ProductListDto;
import com.shopping.common.entity.Product;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {BrandSelectMapper.class, CategorySelectMapper.class}
)
public interface ProductListMapper {

    ProductListDto productToProductListDto(Product product);
}
