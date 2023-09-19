package com.shopping.admin.dto.mapper;

import com.shopping.admin.dto.BrandFormDto;
import com.shopping.common.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface BrandFormMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "logo", source = "logo")
    @Mapping(target = "categories", source = "categories")
    BrandFormDto brandToBrandFormDto(Brand brand);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "logo", source = "logo")
    @Mapping(target = "categories", source = "categories")
    Brand brandFormDtoToBrand(BrandFormDto brandFormDto);
}
