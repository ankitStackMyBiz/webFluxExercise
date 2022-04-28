package com.example.webfluxdemo.mongodb.service;

import com.example.webfluxdemo.mongodb.dto.ProductDto;
import com.example.webfluxdemo.mongodb.repository.ProductRepository;
import com.example.webfluxdemo.mongodb.util.AppUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Flux<ProductDto> getAllProducts() {
        return productRepository.findAll().map(AppUtils::entityToDto);
    }

    public Mono<ProductDto> getProductById(String id) {
        return productRepository.findById(id).map(AppUtils::entityToDto);
    }

    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDtoMono) {
        return productDtoMono.map(AppUtils::dtoToEntity)
                .flatMap(productRepository::insert)
                .map(AppUtils::entityToDto);
    }

    public Mono<ProductDto> updateProduct(Mono<ProductDto> productDtoMono, String id) {
        return productRepository.findById(id)
                .flatMap(p -> productDtoMono.map(AppUtils::dtoToEntity))
                .doOnNext(e -> e.setId(id))
                .flatMap(productRepository::save)
                .map(AppUtils::entityToDto);
    }

    public Mono<Void> deleteProduct(String id){
       return productRepository.deleteById(id);
    }

}
