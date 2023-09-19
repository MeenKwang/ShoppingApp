package com.shopping.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.admin.dto.CategoryDTO;
import com.shopping.admin.dto.CategoryFormDto;
import com.shopping.admin.dto.CategoryListDto;
import com.shopping.admin.dto.CategorySelect;
import com.shopping.admin.dto.mapper.CategoryFormMapper;
import com.shopping.admin.dto.mapper.CategoryMapper;
import com.shopping.admin.service.CategoryService;
import com.shopping.admin.util.FileUploadUtil;
import com.shopping.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryRestController {

    private final CategoryService categoryService;
    private final CategoryFormMapper categoryFormMapper;

    @Autowired

    public CategoryRestController(CategoryService categoryService, CategoryFormMapper categoryFormMapper) {
        this.categoryService = categoryService;
        this.categoryFormMapper = categoryFormMapper;
    }

    @GetMapping("categories")
    public ResponseEntity<CategoryListDto> listFirstPage() {
        return listByPage(1, "name", "asc", null, 5);
    }

    @GetMapping("categories/page/{pageNum}")
    public ResponseEntity<CategoryListDto> listByPage(@PathVariable("pageNum") int pageNum, @RequestParam("sortField") String sortField,
                                                        @RequestParam("sortDir") String sortDir, @RequestParam(value = "nameSearch", required = false) String nameSearch,
                                                        @RequestParam("pageSize") int pageSize)
    {
        CategoryListDto categoryListDtos = categoryService.listByPage(pageNum, sortField, sortDir, nameSearch, pageSize);
        return ResponseEntity.ok(categoryListDtos);
    }

    @GetMapping("categories/list_categories_used_in_form")
    public ResponseEntity<List<CategorySelect>> listCategoriesUsedInForm() {
        List<CategorySelect> categorySelectList = categoryService.listCategoriesUsedInForm();
        return ResponseEntity.ok(categorySelectList);
    }

    @PostMapping("categories/save")
    public  ResponseEntity<Category> saveCategory(@RequestParam("categoryForm") String body,
                                                     @RequestParam(value = "image", required = true) MultipartFile image) {
        try {
            CategoryFormDto categoryDTO = new ObjectMapper().readValue(body, CategoryFormDto.class);
            if(image != null) {
                String fileName = StringUtils.cleanPath(image.getOriginalFilename());
                categoryDTO.setImage(fileName);
                Category category = categoryFormMapper.categoryFormDtoToCategory(categoryDTO);
                Category savedCategory = categoryService.save(category);
                String uploadDir = "../category-images/" + savedCategory.getId();
                FileUploadUtil.cleanDir(uploadDir);
                FileUploadUtil.saveFile(uploadDir, fileName, image);
                return ResponseEntity.ok().body(category);
            } else {
                if(categoryDTO.getId() != null) {
                    Category savedCategory = categoryService.save(categoryFormMapper.categoryFormDtoToCategory(categoryDTO));
                    return ResponseEntity.ok().body(savedCategory);
                }
                return ResponseEntity.badRequest().body(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("categories/delete/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable("id") Integer id) {
        try {
            categoryService.delete(id);
            String categoryDir = "../category-images/" + id;
            FileUploadUtil.removeDir(categoryDir);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(false);
        }
    }

    @GetMapping("categories/{id}")
    public ResponseEntity<CategoryFormDto> getCategoryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(categoryService.getCategoryDtoById(id));
    }
}
