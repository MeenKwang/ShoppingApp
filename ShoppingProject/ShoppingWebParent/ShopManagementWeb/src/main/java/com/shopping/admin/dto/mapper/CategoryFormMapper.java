package com.shopping.admin.dto.mapper;

import com.shopping.admin.dto.CategoryDTO;
import com.shopping.admin.dto.CategoryFormDto;
import com.shopping.common.entity.Category;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface CategoryFormMapper {

    CategoryFormDto categoryToCategoryFormDto(Category category);

    Category categoryFormDtoToCategory(CategoryFormDto categoryFormDto);
}
