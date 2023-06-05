package com.web.reactive.controls;

import com.web.reactive.audit.annotation.Audited;
import com.web.reactive.models.Product;
import com.web.reactive.models.UserProducts;
import com.web.reactive.models.Users;
import com.web.reactive.services.ProductService;
import com.web.reactive.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("common")
public class CommonRestController {

    private ProductService productService;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Audited
    @GetMapping("userProducts")
    public UserProducts getUserProducts() {
        List<Users> usersList =
                userService.getAllUsers()
                        .collectList()
                        .block();
        List<Product> productList =
                productService.getAllProducts()
                        .collectList()
                        .block();
        UserProducts userProducts = new UserProducts();
        userProducts.setProductList(productList);
        userProducts.setUsersList(usersList);
        return userProducts;
    }
}
