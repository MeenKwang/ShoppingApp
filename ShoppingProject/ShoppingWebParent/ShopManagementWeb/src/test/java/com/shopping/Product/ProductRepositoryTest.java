package com.shopping.Product;

import com.shopping.admin.repository.ProductImageRepository;
import com.shopping.admin.repository.ProductRepository;
import com.shopping.common.entity.Product;
import com.shopping.common.entity.ProductImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductImageRepository productImageRepository;
    @Test
    public void testGetPageProductIfCategoryIdNotEqual0() {
        Sort sort = Sort.by("id");
        sort = sort.ascending();
        Pageable pageable = PageRequest.of(0, 15, sort);
        Page<Product> page = productRepository.listByPageIfCategoryIdNull(pageable, "", 0);
        List<Product> productList = page.getContent();
//        System.out.println(productList);
//        assertThat(productList).isNotEmpty();
        assertThat(productList.size()).isEqualTo(15);
    }

    @Test
    public void testGetPageProductIfCategoryIdEqual0() {
        Sort sort = Sort.by("id");
        sort = sort.ascending();
        Pageable pageable = PageRequest.of(0, 15, sort);
        String categoryIdMatch = "-16-";
        Page<Product> page = productRepository.listByPageIfCategoryIdNotNull(pageable, "", categoryIdMatch, 0);
        List<Product> productList = page.getContent();
        productList.forEach(product -> System.out.println(product.getCategory().getId()));
        assertThat(productList).isNotEmpty();
    }
    @Test
    public void changeFirstNameOfProductImage() {
        ProductImage firstProductImage = productImageRepository.findFirstByOrderByIdAsc().get();
        String dir = "../product-images/" + firstProductImage.getProduct().getId() + "/extras/" + firstProductImage.getName();
        File imageFile = new File(dir);
        String changeDir = "../product-images/" + firstProductImage.getProduct().getId() + "/extras/1.png";
        File changeFile = new File(changeDir);
        boolean flag = imageFile.renameTo(changeFile);
        firstProductImage.setName("1.png");
        // persisted already so don't save to save
//        productImageRepository.save(firstProductImage);
        assertThat(flag).isTrue();
    }
    @Test
    public void changeNameOfAllProductImage() {
        List<ProductImage> images = productImageRepository.findAll();
        Integer c = 2;
        for(int i = 1; i < images.size(); i++) {
            if(images.get(i).getProduct().getId() != images.get(i-1).getProduct().getId()) {
                c = 1;
            }
            String dir = "../product-images/" + images.get(i).getProduct().getId() + "/extras/" + images.get(i).getName();
            File imageFile = new File(dir);
            String changeDir = "../product-images/" + images.get(i).getProduct().getId() + "/extras/" + c + ".png";
            File changeFile = new File(changeDir);
            boolean flag = imageFile.renameTo(changeFile);
            images.get(i).setName(c + ".png");
            c++;
        }
        assertThat(images).isNotEmpty();
    }
}
