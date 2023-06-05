package com.web.reactive.models;

import java.util.List;

public class UserProducts {

    private List<Users> usersList;
    private List<Product> productList;

    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
