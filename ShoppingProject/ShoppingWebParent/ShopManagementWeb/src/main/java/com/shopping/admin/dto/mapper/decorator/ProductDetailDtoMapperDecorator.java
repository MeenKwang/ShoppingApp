package com.shopping.admin.dto.mapper.decorator;

import com.shopping.admin.dto.ProductDetailDto;
import com.shopping.admin.dto.mapper.ProductDetailDtoMapper;
import com.shopping.common.entity.Product;
import com.shopping.common.entity.ProductDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class ProductDetailDtoMapperDecorator implements ProductDetailDtoMapper {

    @Autowired
    @Qualifier("delegate")
    private ProductDetailDtoMapper productDetailDtoMapper;

    @Override
    public ProductDetail productDetailDtoToProductDetail(ProductDetailDto productDetailDto) {
        ProductDetail productDetail = productDetailDtoMapper.productDetailDtoToProductDetail(productDetailDto);
//        Product product = new Product();
//        if(productDetailDto.getProductId() != null) {
//            product.setId(productDetailDto.getProductId());
//            productDetail.setProduct(product);
//        } else {
//            productDetail.setProduct(null);
//        }
        return productDetail;
    }
}
