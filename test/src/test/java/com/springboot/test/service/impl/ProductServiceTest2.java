package com.springboot.test.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.springboot.test.data.dto.ProductDto;
import com.springboot.test.data.dto.ProductResponseDto;
import com.springboot.test.data.entity.Product;
import com.springboot.test.data.repository.ProductRepository;
import com.springboot.test.service.ProductService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(ProductServiceImpl.class)
public class ProductServiceTest2 {

    @MockBean
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Test
    public void getProductTest() {
        // given
        Product givenProduct = new Product();
        givenProduct.setNumber(123L);
        givenProduct.setName("pen");
        givenProduct.setPrice(1000);
        givenProduct.setStock(123);

        given(productRepository.findById(123L)).willReturn(Optional.of(givenProduct));

        // when
        ProductResponseDto productResponseDto = productService.getProduct(123L);

        // then
        assertThat(productResponseDto.getNumber()).isEqualTo(givenProduct.getNumber());
        assertThat(productResponseDto.getName()).isEqualTo(givenProduct.getName());
        assertThat(productResponseDto.getPrice()).isEqualTo(givenProduct.getPrice());
        assertThat(productResponseDto.getStock()).isEqualTo(givenProduct.getStock());

        verify(productRepository).findById(123L);
    }

    @Test
    void saveProductTest() {
        // given
        given(productRepository.save(any(Product.class)))
            .will(returnsFirstArg());

        // when
        ProductResponseDto productResponseDto = productService.saveProduct(
            new ProductDto("펜", 1000, 1234));

        // then
        assertThat(productResponseDto.getName()).isEqualTo("펜");
        assertThat(productResponseDto.getPrice()).isEqualTo(1000);
        assertThat(productResponseDto.getStock()).isEqualTo(1234);

        verify(productRepository).save(any());
    }

}
