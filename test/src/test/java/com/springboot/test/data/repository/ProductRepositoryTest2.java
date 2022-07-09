package com.springboot.test.data.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.springboot.test.data.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductRepositoryTest2 {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void basicCRUDTest() {
        /* create */
        // given
        Product givenProduct = Product.builder()
            .name("노트")
            .price(1000)
            .stock(500)
            .build();

        // when
        Product savedProduct = productRepository.save(givenProduct);

        // then
        assertThat(savedProduct.getNumber()).isEqualTo(givenProduct.getNumber());
        assertThat(savedProduct.getName()).isEqualTo(givenProduct.getName());
        assertThat(savedProduct.getPrice()).isEqualTo(givenProduct.getPrice());
        assertThat(savedProduct.getStock()).isEqualTo(givenProduct.getStock());

        /* read */
        // when
        Product selectedProduct = productRepository.findById(savedProduct.getNumber())
            .orElseThrow(RuntimeException::new);

        // then
        assertThat(selectedProduct.getNumber()).isEqualTo(givenProduct.getNumber());
        assertThat(selectedProduct.getName()).isEqualTo(givenProduct.getName());
        assertThat(selectedProduct.getPrice()).isEqualTo(givenProduct.getPrice());
        assertThat(selectedProduct.getStock()).isEqualTo(givenProduct.getStock());

        /* update */
        // when
        Product foundProduct = productRepository.findById(selectedProduct.getNumber())
            .orElseThrow(RuntimeException::new);

        foundProduct.setName("장난감");

        Product updatedProduct = productRepository.save(foundProduct);

        // then
        assertThat(updatedProduct.getName()).isEqualTo("장난감");

        /* delete */
        // when
        productRepository.delete(updatedProduct);

        // then
        assertThat(productRepository.findById(selectedProduct.getNumber())).isEmpty();
    }
}
