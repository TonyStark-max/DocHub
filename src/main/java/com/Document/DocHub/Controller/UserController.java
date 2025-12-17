package com.Document.DocHub.Controller;

import com.Document.DocHub.DTO.Request;
import com.Document.DocHub.Repositories.UserRepo;
import com.Document.DocHub.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;
    private final UserRepo userRepo;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Request request){
        String result= userService.registerUser(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity <String> login(@RequestBody Request request){
        return ResponseEntity.ok(
                userService.loginUser(request.getEmail(), request.getPassword())
        );
    }
    @DeleteMapping("/deleteUser/{name}")
    @PreAuthorize("hasAuthorize('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable String name){
        String res=userService.deleteUser(name);
        return ResponseEntity.ok("User Deleted Successfully "+res);
    }
}