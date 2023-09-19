package com.shopping.admin.dto.mapper;

import com.shopping.admin.dto.BrandSelectDto;
import com.shopping.common.entity.Brand;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface BrandSelectMapper {
    BrandSelectDto brandToBrandSelectDto(Brand brand);

    Brand BrandSelectDtoToBrand(BrandSelectDto brandSelectDto);
}
