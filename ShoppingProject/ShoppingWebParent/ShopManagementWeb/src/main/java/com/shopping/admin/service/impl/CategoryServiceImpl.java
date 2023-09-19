package com.shopping.admin.service.impl;

import com.shopping.admin.dto.CategoryDTO;
import com.shopping.admin.dto.CategoryFormDto;
import com.shopping.admin.dto.CategoryListDto;
import com.shopping.admin.dto.CategorySelect;
import com.shopping.admin.dto.mapper.CategoryFormMapper;
import com.shopping.admin.dto.mapper.CategoryMapper;
import com.shopping.admin.dto.mapper.CategorySelectMapper;
import com.shopping.admin.repository.CategoryRepository;
import com.shopping.admin.service.CategoryService;
import com.shopping.common.entity.Category;
import com.shopping.common.exception.CategoryNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategorySelectMapper categorySelectMapper;
    private final CategoryFormMapper categoryFormMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper,
                               CategorySelectMapper categorySelectMapper, CategoryFormMapper categoryFormMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.categorySelectMapper = categorySelectMapper;
        this.categoryFormMapper = categoryFormMapper;
    }

    @Override
    public CategoryListDto listByPage(int pageNum, String sortField, String sortDir, String nameSearch, int pageSize) {
        Sort sort = Sort.by(sortField);
        if(sortDir.equals("asc")) {
            sort = sort.ascending();
        } else if(sortDir.equals("desc")) {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<Category> pageCategories = null;
        Page<CategoryDTO> pageCategoryDtos = null;

        if(nameSearch != null && !nameSearch.isEmpty()) {
            pageCategories = categoryRepository.search(nameSearch, pageable);
        } else {
            pageCategories =  categoryRepository.findRootCategories(pageable);
        }

        List<Category> rootCategories = pageCategories.getContent();
        CategoryListDto categoryListDto = new CategoryListDto();
        categoryListDto.setTotalElements(pageCategories.getTotalElements());
        categoryListDto.setTotalPages(pageCategories.getTotalPages());

        if(nameSearch != null && !nameSearch.isEmpty()) {
            List<CategoryDTO> dtosList = rootCategories.stream()
                    .map(category -> categoryMapper.categoryToCategoryDTO(category)).collect(Collectors.toList());
            categoryListDto.setCategoriesList(dtosList);
            return categoryListDto;
        } else {
            List<CategoryDTO> lstCategories = listHierarchicalCategories(rootCategories, sortDir, sortField);
            categoryListDto.setCategoriesList(lstCategories);
            return categoryListDto;
        }

    }

    private List<CategoryDTO> listHierarchicalCategories(List<Category> rootCategories, String sortDir, String sortField) {
        List<CategoryDTO> hierarchicalCategories = new ArrayList<>();

        for(Category rootCategory: rootCategories) {
            hierarchicalCategories.add(categoryMapper.categoryToCategoryDTO(rootCategory));

            Set<Category> children = sortSubCategories(rootCategory.getChildren(), sortDir, sortField);

            for(Category subCategory : children) {
                String name = "--" + subCategory.getName();
                CategoryDTO subCategoryDto = categoryMapper.categoryToCategoryDTO(subCategory);
                subCategoryDto.setName(name);
                hierarchicalCategories.add(subCategoryDto);

                listSubHierarchicalCategories(hierarchicalCategories, subCategory, 1, sortDir, sortField);
            }
        }

        return hierarchicalCategories;
    }

    private void listSubHierarchicalCategories(List<CategoryDTO> hierarchicalCategories, Category parent, int subLevel, String sortDir, String sortField) {
        Set<Category> children = sortSubCategories(parent.getChildren(), sortDir, sortField);
        int newSubLevel = subLevel + 1;

        for(Category subCategory : children) {
            String name = "";
            for(int i = 0; i < newSubLevel; i++) {
                name += "--";
            }
            CategoryDTO subCategoryDto = categoryMapper.categoryToCategoryDTO(subCategory);
            subCategoryDto.setName(name + subCategory.getName());
            hierarchicalCategories.add(subCategoryDto);

            listSubHierarchicalCategories(hierarchicalCategories, subCategory, newSubLevel, sortDir, sortField);
        }
    }

    private SortedSet<Category> sortSubCategories(Set<Category> children, String sortDir, String sortField) {
        SortedSet<Category> sortedChildren= new TreeSet<>(new Comparator<Category>() {

            @Override
            public int compare(Category o1, Category o2) {
                if(sortDir.equals("asc")) {
                    if(sortField.equals("name")) {
                        return o1.getName().compareTo(o2.getName());
                    } else {
                        return o1.getId().compareTo(o2.getId());
                    }
                } else {
                    if(sortField.equals("name")) {
                        return o1.getName().compareTo(o2.getName());
                    } else {
                        return o1.getId().compareTo(o2.getId());
                    }
                }

            }

        });
        sortedChildren.addAll(children);
        return sortedChildren;
    }

    @Override
    public List<CategorySelect> listCategoriesUsedInForm(){
        List<CategorySelect> categoriesUsedInForm = new ArrayList<>();
        List<Category> categoriesInDB =  categoryRepository.findRootCategories(Sort.by("name").ascending());
        for(Category category: categoriesInDB) {
            if(category.getParent() == null) {
                categoriesUsedInForm.add(categorySelectMapper.categoryToCategorySelect(category));
                Set<Category> children = sortSubCategories(category.getChildren(), "asc", "name");

                for(Category subCategory: children) {
                    String name = "--" + subCategory.getName();
                    CategorySelect categorySelect = categorySelectMapper.categoryToCategorySelect(subCategory);
                    categorySelect.setName(name);
                    categoriesUsedInForm.add(categorySelect);
                    listSubCategoryUsedInForm(categoriesUsedInForm, subCategory, 1);
                }
            }
        }
        return categoriesUsedInForm;
    }

    private void listSubCategoryUsedInForm(List<CategorySelect> categoriesUsedInForm, Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = sortSubCategories(parent.getChildren(), "asc", "name");
        for(Category subCategory: children) {
            String name = "";
            for(int i = 0; i < newSubLevel; i++) {
                name += "--";
            }
            name += subCategory.getName();
            CategorySelect categorySelect = categorySelectMapper.categoryToCategorySelect(subCategory);
            categorySelect.setName(name);
            categoriesUsedInForm.add(categorySelect);

            listSubCategoryUsedInForm(categoriesUsedInForm, subCategory, newSubLevel);
        }
    }

    @Override
    public Category save(Category category) {
        Integer parentId = category.getParent().getId();
        Category parent = categoryRepository.findById(parentId).orElse(null);
        if(parent != null) {
            String allParentIds = parent.getAllParentIDs() == null ? "-" : parent.getAllParentIDs();
            allParentIds += String.valueOf(parent.getId()) + "-";
            category.setAllParentIDs(allParentIds);
        }
        return categoryRepository.save(category);
    }

    @Override
    public boolean checkUnique(Integer id, String name, String alias) {
        return false;
    }

    @Override
    public void delete(Integer id) throws CategoryNotFoundException {
        boolean exist = categoryRepository.existsById(id);

        if(!exist) {
            throw new CategoryNotFoundException("Cannot find category with the ID " + id);
        }

        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryFormDto getCategoryDtoById(Integer id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if(category != null) {
            CategoryFormDto categoryFormDto = categoryFormMapper.categoryToCategoryFormDto(category);
            return categoryFormDto;
        }
        return null;
    }
}
