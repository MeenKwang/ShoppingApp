package com.shopping.admin.dto;

public class ProductListDto {
    private Integer id;
    private String mainImage;
    private String name;
    private BrandSelectDto brand;
    private CategorySelect category;
    private boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BrandSelectDto getBrand() {
        return brand;
    }

    public void setBrand(BrandSelectDto brand) {
        this.brand = brand;
    }

    public CategorySelect getCategory() {
        return category;
    }

    public void setCategory(CategorySelect category) {
        this.category = category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
