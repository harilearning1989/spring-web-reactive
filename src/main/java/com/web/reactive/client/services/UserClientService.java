package com.web.reactive.client.services;

import com.web.reactive.models.Users;
import org.springframework.stereotype.Service;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Flux;

@Service
@HttpExchange(url = "", accept = "application/json", contentType = "application/json")
public interface UserClientService {

    @GetExchange("")
    Flux<Users> getAllUsers();
}
