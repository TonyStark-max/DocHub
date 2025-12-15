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
    public ResponseEntity<Map<String,String>> login(@RequestBody Request request){
        try {
            String token = userService.loginUser(request.getEmail(), request.getPassword(),request.getRole());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String,String> error=new HashMap<>();
            error.put("error",e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }
    @DeleteMapping("/deleteUser/{name}")
    @PreAuthorize("hasAuthorize('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable String name){
        String res=userService.deleteUser(name);
        return ResponseEntity.ok("User Deleted Successfully "+res);
    }
}