package com.shopping.admin.dto;

import java.util.List;

public class CategoryListDto {
    private List<CategoryDTO> categoriesList;
    private int totalPages;
    private long totalElements;

    public List<CategoryDTO> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<CategoryDTO> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}
