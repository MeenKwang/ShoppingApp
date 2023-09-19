package com.shopping.admin.dto;

import com.shopping.common.entity.ProductDetail;
import com.shopping.common.entity.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Set;

public class ProductFormDto {
    public ProductFormDto() {
    }

    private Integer id;
    private String name;
    private String alias;
    private String shortDescription;
    private String fullDescription;
    private boolean enabled;
    private boolean inStock;
    private float cost;
    private float price;
    private float discountPercent;
    private float length;
    private float width;
    private float height;
    private float weight;
    private String mainImage;
    private BrandSelectDto brand;
    private CategorySelect category;
    private Set<ProductExtraImageDto> extraImages;
    private List<ProductDetailDto> productDetails;

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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
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

    public Set<ProductExtraImageDto> getExtraImages() {
        return extraImages;
    }

    public void setExtraImages(Set<ProductExtraImageDto> extraImages) {
        this.extraImages = extraImages;
    }

    public List<ProductDetailDto> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<ProductDetailDto> productDetails) {
        this.productDetails = productDetails;
    }

    @Override
    public String toString() {
        return "ProductFormDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", fullDescription='" + fullDescription + '\'' +
                ", enabled=" + enabled +
                ", inStock=" + inStock +
                ", cost=" + cost +
                ", price=" + price +
                ", discountPercent=" + discountPercent +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", weight=" + weight +
                ", mainImage='" + mainImage + '\'' +
                ", brand=" + brand +
                ", category=" + category +
                ", extraImages=" + extraImages +
                ", productDetails=" + productDetails +
                '}';
    }
}
