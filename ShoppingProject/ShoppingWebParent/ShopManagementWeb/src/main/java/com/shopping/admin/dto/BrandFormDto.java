package com.shopping.admin.dto;

import com.shopping.common.entity.Category;

import java.util.Set;

public class BrandFormDto {
    private Integer id;
    private String name;
    private String logo;
    private Set<CategorySelect> categories;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Set<CategorySelect> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategorySelect> categories) {
        this.categories = categories;
    }
}
