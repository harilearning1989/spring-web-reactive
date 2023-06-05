package com.web.reactive.services;

import com.web.reactive.models.Users;
import reactor.core.publisher.Flux;

public interface UserService {
    Flux<Users> getAllUsers();
}
