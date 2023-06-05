package com.web.reactive.services;

import com.web.reactive.client.services.UserClientService;
import com.web.reactive.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class UserServiceImpl implements UserService {

    private UserClientService userClientService;

    @Autowired
    public void setUserClientService(UserClientService userClientService) {
        this.userClientService = userClientService;
    }

    @Override
    public Flux<Users> getAllUsers() {
        return userClientService.getAllUsers();
    }
}
