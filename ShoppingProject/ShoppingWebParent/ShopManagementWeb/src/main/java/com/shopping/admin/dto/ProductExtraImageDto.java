package com.shopping.admin.dto;

public class ProductExtraImageDto {
    private Integer id;
    private String name;
    private Integer productId;
    private int order;

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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "ProductExtraImageDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productId=" + productId +
                ", order=" + order +
                '}';
    }
}
