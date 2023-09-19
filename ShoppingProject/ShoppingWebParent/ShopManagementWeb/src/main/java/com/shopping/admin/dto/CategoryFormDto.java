package com.shopping.admin.dto;

import java.util.Set;

public class CategoryFormDto {
    private Integer id;
    private String name;
    private String alias;
    private String image;
    private boolean enabled;
    private CategorySelect parent;
    private Set<CategoryFormDto> children;

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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public CategorySelect getParent() {
        return parent;
    }

    public void setParent(CategorySelect parent) {
        this.parent = parent;
    }

    public Set<CategoryFormDto> getChildren() {
        return children;
    }

    public void setChildren(Set<CategoryFormDto> children) {
        this.children = children;
    }
}
