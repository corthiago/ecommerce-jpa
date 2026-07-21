package com.thiago.ecommerce.controller;

import com.thiago.ecommerce.controller.dto.CreateUserDto;
import com.thiago.ecommerce.entity.UserEntity;
import com.thiago.ecommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto dto){
        var user = userService.createUser(dto);
        return ResponseEntity.created(URI.create("/users/" + user.getUserId())).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserEntity> findById(@PathVariable(name = "userId") UUID userId){
        var user = userService.findById(userId);
        return user.isPresent() ?
                ResponseEntity.ok(user.get()) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "userId") UUID userId){
        var deleted = userService.deleteById(userId);

        return deleted ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }
}
