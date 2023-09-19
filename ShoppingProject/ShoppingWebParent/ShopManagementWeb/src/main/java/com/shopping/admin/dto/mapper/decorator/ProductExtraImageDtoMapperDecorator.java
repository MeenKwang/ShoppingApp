package com.shopping.admin.dto.mapper.decorator;

import com.shopping.admin.dto.ProductExtraImageDto;
import com.shopping.admin.dto.mapper.ProductExtraImageDtoMapper;
import com.shopping.common.entity.Product;
import com.shopping.common.entity.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class ProductExtraImageDtoMapperDecorator implements ProductExtraImageDtoMapper {
    @Autowired
    @Qualifier("delegate")
    private ProductExtraImageDtoMapper productExtraImageDtoMapper;

    @Override
    public ProductImage productExtraImageDtoToProductImage(ProductExtraImageDto productExtraImageDto) {
        ProductImage productImage = productExtraImageDtoMapper.productExtraImageDtoToProductImage(productExtraImageDto);
        Product product = new Product();
        if(productExtraImageDto.getProductId() != null) {
            product.setId(productExtraImageDto.getProductId());
            productImage.setProduct(product);
        } else {
            productImage.setProduct(null);
        }
        return productImage;
    }
}
