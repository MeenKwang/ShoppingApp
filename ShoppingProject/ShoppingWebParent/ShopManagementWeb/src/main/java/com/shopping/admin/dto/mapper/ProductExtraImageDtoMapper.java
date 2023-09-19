package com.shopping.admin.dto.mapper;

import com.shopping.admin.dto.ProductExtraImageDto;
import com.shopping.admin.dto.mapper.decorator.ProductExtraImageDtoMapperDecorator;
import com.shopping.common.entity.ProductImage;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@DecoratedWith(ProductExtraImageDtoMapperDecorator.class)
public interface ProductExtraImageDtoMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "order", source = "order")
    ProductExtraImageDto productImageToProductExtraImage(ProductImage productImage);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "order", source = "order")
    ProductImage productExtraImageDtoToProductImage(ProductExtraImageDto productExtraImageDto);
}
