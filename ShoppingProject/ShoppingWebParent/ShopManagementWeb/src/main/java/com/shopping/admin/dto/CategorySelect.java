package com.shopping.admin.dto;

public class CategorySelect {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategorySelect() {}

    @Override
    public String toString() {
        return "CategorySelect{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
