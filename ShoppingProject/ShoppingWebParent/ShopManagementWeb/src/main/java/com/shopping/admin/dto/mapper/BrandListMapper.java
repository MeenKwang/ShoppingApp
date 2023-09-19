package com.shopping.admin.dto.mapper;

import com.shopping.admin.dto.BrandListDto;
import com.shopping.common.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface BrandListMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "logo", source = "logo")
    @Mapping(target = "categories", source = "categories")
    BrandListDto brandToBrandListDto(Brand brand);
}
