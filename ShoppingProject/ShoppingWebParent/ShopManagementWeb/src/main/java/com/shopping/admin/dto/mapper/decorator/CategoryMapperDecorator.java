package com.shopping.admin.dto.mapper.decorator;

import com.shopping.admin.dto.CategoryDTO;
import com.shopping.admin.dto.mapper.CategoryMapper;
import com.shopping.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class CategoryMapperDecorator implements CategoryMapper {

    @Autowired
    @Qualifier("delegate")
    private CategoryMapper categoryMapper;

    @Override
    public CategoryDTO categoryToCategoryDTO(Category category) {
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);
        categoryDTO.setHasChildren(category.getChildren().size() > 0);
        return  categoryDTO;
    }
}
