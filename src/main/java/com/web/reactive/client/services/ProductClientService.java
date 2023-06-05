package com.web.reactive.client.services;

import com.web.reactive.models.Product;
import com.web.reactive.models.Products;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@HttpExchange(url = "", accept = "application/json", contentType = "application/json")
public interface ProductClientService {

    @GetExchange("")
    Flux<Product> getAllProducts();

    @GetExchange("/{id}")
    Mono<Products> getProductById(@PathVariable("id") int id);

    @PostExchange("/add")
    Mono<ResponseEntity<Void>> saveProduct(@RequestBody Products products);

    @PutExchange("/{id}")
    Mono<ResponseEntity<Void>> update(@PathVariable Long id, @RequestBody Products user);

    @DeleteExchange("/{id}")
    Mono<ResponseEntity<Void>> delete(@PathVariable Long id);
}
