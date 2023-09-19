package com.shopping.admin.dto.mapper;

import com.shopping.admin.dto.ProductDetailDto;
import com.shopping.admin.dto.mapper.decorator.ProductDetailDtoMapperDecorator;
import com.shopping.common.entity.ProductDetail;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@DecoratedWith(ProductDetailDtoMapperDecorator.class)
public interface ProductDetailDtoMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "value", source = "value")
    @Mapping(target = "productId", source = "product.id")
    ProductDetailDto productDetailToProductDetailDto(ProductDetail productDetail);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "value", source = "value")
    ProductDetail productDetailDtoToProductDetail(ProductDetailDto productDetailDto);
}
