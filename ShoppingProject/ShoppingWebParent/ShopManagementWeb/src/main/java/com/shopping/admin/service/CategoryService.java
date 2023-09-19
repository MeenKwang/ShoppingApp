package com.shopping.admin.service;

import com.shopping.admin.dto.CategoryDTO;
import com.shopping.admin.dto.CategoryFormDto;
import com.shopping.admin.dto.CategoryListDto;
import com.shopping.admin.dto.CategorySelect;
import com.shopping.common.entity.Category;
import com.shopping.common.exception.CategoryNotFoundException;

import java.util.List;

public interface CategoryService {
    public CategoryListDto listByPage(int pageNum, String sortField, String sortDir, String nameSearch,
                                      int pageSize);

    public List<CategorySelect> listCategoriesUsedInForm();
    public Category save(Category category);

    public boolean checkUnique(Integer id, String name, String alias);

    public void delete(Integer id) throws CategoryNotFoundException;

    public CategoryFormDto getCategoryDtoById(Integer id);
}
