package com.web.reactive.controls;

import com.web.reactive.audit.annotation.Audited;
import com.web.reactive.dtos.User;
import com.web.reactive.models.Product;
import com.web.reactive.models.UserProducts;
import com.web.reactive.models.Users;
import com.web.reactive.services.GitHubLookupService;
import com.web.reactive.services.ProductService;
import com.web.reactive.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("common")
public class CommonRestController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private ProductService productService;
    private UserService userService;
    @Autowired
    private GitHubLookupService gitHubLookupService;

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

    @GetMapping("asyncMethods")
    public void callAsyncMethods() {
        // Start the clock
        long start = System.currentTimeMillis();
        try {
            CompletableFuture<User> page1 = gitHubLookupService.findUser("PivotalSoftware");
            CompletableFuture<User> page2 = gitHubLookupService.findUser("CloudFoundry");
            CompletableFuture<User> page3 = gitHubLookupService.findUser("Spring-Projects");
            CompletableFuture<User> page4 = gitHubLookupService.findUser("RameshMF");
            // Wait until they are all done
            CompletableFuture.allOf(page1, page2, page3, page4).join();

            // Print results, including elapsed time
            LOGGER.info("Elapsed time: " + (System.currentTimeMillis() - start));
            LOGGER.info("--> " + page1.get());
            LOGGER.info("--> " + page2.get());
            LOGGER.info("--> " + page3.get());
            LOGGER.info("--> " + page4.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
