package com.shopping.admin.dto.mapper;

import com.shopping.admin.dto.CategorySelect;
import com.shopping.common.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface CategorySelectMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CategorySelect categoryToCategorySelect(Category category);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Category categorySelectToCategory(CategorySelect categorySelect);
}
