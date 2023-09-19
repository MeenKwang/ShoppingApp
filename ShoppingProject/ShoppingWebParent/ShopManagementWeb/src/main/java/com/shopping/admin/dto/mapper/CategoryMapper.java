package com.shopping.admin.dto.mapper;

import com.shopping.admin.dto.CategoryDTO;
import com.shopping.admin.dto.mapper.decorator.CategoryMapperDecorator;
import com.shopping.common.entity.Category;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
@DecoratedWith(CategoryMapperDecorator.class)
public interface CategoryMapper {
    @Mapping(target = "hasChildren", ignore = true)
    CategoryDTO categoryToCategoryDTO(Category category);

    Category categoryDTOToCategory(CategoryDTO categoryDTO);
}
