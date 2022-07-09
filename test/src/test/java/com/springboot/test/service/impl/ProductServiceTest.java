package com.springboot.test.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.springboot.test.data.dto.ProductDto;
import com.springboot.test.data.dto.ProductResponseDto;
import com.springboot.test.data.entity.Product;
import com.springboot.test.data.repository.ProductRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ProductServiceTest {

    private ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUpTest() {
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void getProductTest() {
        Product givenProduct = new Product();
        givenProduct.setNumber(123L);
        givenProduct.setName("pen");
        givenProduct.setPrice(1000);
        givenProduct.setStock(123);

        Mockito.when(productRepository.findById(123L))
            .thenReturn(Optional.of(givenProduct));

        ProductResponseDto productResponseDto = productService.getProduct(123L);

        assertThat(productResponseDto.getNumber()).isEqualTo(givenProduct.getNumber());
        assertThat(productResponseDto.getName()).isEqualTo(givenProduct.getName());
        assertThat(productResponseDto.getPrice()).isEqualTo(givenProduct.getPrice());
        assertThat(productResponseDto.getStock()).isEqualTo(givenProduct.getStock());

        verify(productRepository).findById(123L);
    }

    @Test
    void saveProductTest() {
        Mockito.when(productRepository.save(any(Product.class)))
            .then(returnsFirstArg());

        ProductResponseDto productResponseDto = productService.saveProduct(
            new ProductDto("펜", 1000, 1234));

        assertThat(productResponseDto.getName()).isEqualTo("펜");
        assertThat(productResponseDto.getPrice()).isEqualTo(1000);
        assertThat(productResponseDto.getStock()).isEqualTo(1234);
    }
}
