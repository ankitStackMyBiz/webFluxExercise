package com.example.webfluxdemo.mongodb.controller;

import com.example.webfluxdemo.mongodb.dto.ProductDto;
import com.example.webfluxdemo.mongodb.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Flux<ProductDto>> getProducts() {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<ProductDto>> getProductById(@PathVariable String id) {
        return new ResponseEntity<>(service.getProductById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Mono<ProductDto>> createProduct(@RequestBody Mono<ProductDto> productDto) {
        return new ResponseEntity<>(service.saveProduct(productDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mono<ProductDto>> updateProduct(@RequestBody Mono<ProductDto> productDto, @PathVariable String id) {
        return new ResponseEntity<>(service.updateProduct(productDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> deleteProduct(@PathVariable String id) {
        return new ResponseEntity<>(service.deleteProduct(id), HttpStatus.NO_CONTENT);
    }
}
