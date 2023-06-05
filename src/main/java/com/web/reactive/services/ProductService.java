package com.web.reactive.services;

import com.web.reactive.models.Product;
import com.web.reactive.models.Products;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Flux<Product> getAllProducts();

    Mono<Products> getProductById(int id);

    Mono<ResponseEntity<Void>> saveProduct(Products products);
}
