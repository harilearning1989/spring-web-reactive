package com.web.reactive.controls;

import com.web.reactive.models.Product;
import com.web.reactive.models.Products;
import com.web.reactive.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product")
public class ProductRestController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("list")
    public Flux<Product> getAllProducts() {
        return productService.getAllProducts();
        /*List<User> userList = flux
                .collect(Collectors.toList())
                .share().block();
        return userList;*/
    }

    @GetMapping("byId")
    public Mono<Products> getProductById(@RequestParam("id") int id) {
        return productService.getProductById(id);
    }

    @PostMapping("saveProduct")
    public Mono<ResponseEntity<Void>> saveProduct(
            @RequestBody Products products) {
        return productService.saveProduct(products);
    }

    /*@DeleteMapping("delete")
    public void delete() {
        userClient.delete(1L).subscribe(
                data -> log.info("User: {}", data)
        );
    }*/

}