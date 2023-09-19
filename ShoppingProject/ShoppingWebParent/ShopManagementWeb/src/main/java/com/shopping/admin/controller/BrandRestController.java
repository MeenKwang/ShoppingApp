package com.shopping.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.admin.dto.BrandFormDto;
import com.shopping.admin.dto.BrandListDto;
import com.shopping.admin.dto.BrandSelectDto;
import com.shopping.admin.dto.CategorySelect;
import com.shopping.admin.dto.mapper.BrandFormMapper;
import com.shopping.admin.dto.mapper.CategorySelectMapper;
import com.shopping.admin.exception.BrandNotFoundException;
import com.shopping.admin.exception.BrandNotFoundRestException;
import com.shopping.admin.service.BrandService;
import com.shopping.admin.util.FileUploadUtil;
import com.shopping.common.entity.Brand;
import com.shopping.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class BrandRestController {
    private final BrandService brandService;
    private final BrandFormMapper brandFormMapper;
    private final CategorySelectMapper categorySelectMapper;

    @Autowired
    public BrandRestController(BrandService brandService, BrandFormMapper brandFormMapper, CategorySelectMapper categorySelectMapper) {
        this.brandService = brandService;
        this.brandFormMapper = brandFormMapper;
        this.categorySelectMapper = categorySelectMapper;
    }
    @GetMapping("brands")
    public ResponseEntity<Page<BrandListDto>> getFirstPage() {
        return getFirstPage(1, "name", "asc", "", 10);
    }
    @GetMapping("brands/page/{pageNum}")
    public ResponseEntity<Page<BrandListDto>> getFirstPage(@PathVariable("pageNum") Integer pageNum, @RequestParam("sortField") String sortField,
                                                           @RequestParam("sortDir") String sortDir, @RequestParam(value = "nameSearch", required = false) String nameSearch,
                                                           @RequestParam("pageSize") int pageSize) {
        Page<BrandListDto> list = brandService.listByPage(pageNum, sortField, sortDir, nameSearch, pageSize);
        return ResponseEntity.ok(list);
    }

    @GetMapping("brands/{id}")
    public ResponseEntity<BrandFormDto> getById(@PathVariable("id") Integer id) throws BrandNotFoundException {
        try {
            return ResponseEntity.ok().body(brandService.getBrandDtoById(id));
        } catch (Exception e) {
            throw new BrandNotFoundException("Could not find any brand with ID: " + id);
        }
    }

    @PostMapping("brands/save")
    public ResponseEntity<BrandFormDto> save(@RequestParam("brandForm") String body,
                                             @RequestParam(value = "logo", required = false) MultipartFile logo) {
        try {
            BrandFormDto dto = new ObjectMapper().readValue(body, BrandFormDto.class);
            if(logo != null) {
                String fileName = StringUtils.cleanPath(logo.getOriginalFilename());
                dto.setLogo(fileName);
                Brand savedBrand = brandService.save(dto);
                BrandFormDto brandFormDto = brandFormMapper.brandToBrandFormDto(savedBrand);
                String uploadDir = "../brand-logos/" + savedBrand.getId();
                FileUploadUtil.cleanDir(uploadDir);
                FileUploadUtil.saveFile(uploadDir, fileName, logo);
                return ResponseEntity.ok().body(brandFormDto);
            } else {
                if(dto.getId() != null) {
                    Brand savedBrand = brandService.save(dto);
                    BrandFormDto brandFormDto = brandFormMapper.brandToBrandFormDto(savedBrand);
                    return ResponseEntity.ok().body(brandFormDto);
                }
                return ResponseEntity.badRequest().body(null);
            }
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("brands/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        try {
            brandService.delete(id);
            String categoryDir = "../brand-logos/" + id;
            FileUploadUtil.removeDir(categoryDir);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(false);
        }
    }
    @GetMapping("brands/list_brands_used_in_form")
    public ResponseEntity<List<BrandSelectDto>> listBrandsUsedInForm() {
        return ResponseEntity.ok(brandService.listBrandsUsedInForm());
    }
    @GetMapping("brands/{id}/categories")
    public ResponseEntity<List<CategorySelect>> listCategoriesByBrand(@PathVariable("id") Integer brandId) throws BrandNotFoundRestException {
        try {
            Brand brand = brandService.get(brandId);
            Set<Category> categories = brand.getCategories();
            List<CategorySelect> categorySelectSet = new ArrayList<>();
            categories.stream().forEach(category -> {
                CategorySelect categorySelect = categorySelectMapper.categoryToCategorySelect(category);
                categorySelectSet.add(categorySelect);
            });
            return ResponseEntity.ok(categorySelectSet);
        } catch (BrandNotFoundException e) {
            throw new BrandNotFoundRestException();
        }

    }
}
