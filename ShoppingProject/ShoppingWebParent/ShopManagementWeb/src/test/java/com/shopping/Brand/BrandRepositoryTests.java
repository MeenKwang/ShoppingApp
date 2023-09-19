package com.shopping.Brand;

import com.shopping.admin.dto.BrandSelectDto;
import com.shopping.admin.repository.BrandRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class BrandRepositoryTests {
    @Autowired
    private BrandRepository repo;

    @Test
    public void testFindAll() {
        List<BrandSelectDto> brands = repo.findAllWithCustomObject();
        brands.forEach(System.out::println);

        assertThat(brands).isNotEmpty();
    }
}
