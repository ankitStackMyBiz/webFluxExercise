package com.example.webfluxdemo;


import com.example.webfluxdemo.mongodb.controller.ProductController;
import com.example.webfluxdemo.mongodb.dto.ProductDto;
import com.example.webfluxdemo.mongodb.repository.ProductRepository;
import com.example.webfluxdemo.mongodb.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(ProductController.class)
class ProductTest {

    @Autowired
    private WebTestClient webTestClient;

    @Mock
    ProductRepository productRepository;

    @MockBean
    ProductService service;

    @Test
    public void addProductTest() {
        Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("1", "Tv", 2, 1000));
        when(service.saveProduct(productDtoMono)).thenReturn(productDtoMono);

        webTestClient.post().uri("/products")
                .body(Mono.just(productDtoMono), ProductDto.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void getProductsTest(){
        Flux<ProductDto> productDtoFlux = Flux.just(new ProductDto("1", "Tv", 2, 1000),
                new ProductDto("2", "Phone", 1, 2000));
        when(service.getAllProducts()).thenReturn(productDtoFlux);
        Flux<ProductDto> responseBody = webTestClient.get().uri("/products")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new ProductDto("1", "Tv", 2, 1000))
                .expectNext( new ProductDto("2", "Phone", 1, 2000))
                .verifyComplete();
    }

    @Test
    public void getProductByIdTest(){
        Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("1", "Tv", 1, 100000));
        when(service.getProductById(any())).thenReturn(productDtoMono);

        Flux<ProductDto> responseBody = webTestClient.get().uri("/products/1")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(p -> p.getId().equals("1"))
                .verifyComplete();

    }


    @Test
    public void updateProductTest(){
        Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("1", "Tv", 1, 1000));
        when(service.updateProduct(productDtoMono, "1")).thenReturn(productDtoMono);

        webTestClient.put().uri("/products/1")
                .body(Mono.just(productDtoMono), ProductDto.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void deleteProductTest(){
        given(service.deleteProduct(any())).willReturn(Mono.empty());

        webTestClient.delete().uri("/products/1")
                .exchange()
                .expectStatus().isNoContent();
    }


}
