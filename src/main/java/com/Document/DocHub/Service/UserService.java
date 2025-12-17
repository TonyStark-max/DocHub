package com.Document.DocHub.Service;

import com.Document.DocHub.DTO.AuthResponse;
import com.Document.DocHub.DTO.Request;
import com.Document.DocHub.Entity.Role;
import com.Document.DocHub.Entity.User;
import com.Document.DocHub.Repositories.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public String registerUser(Request request){
        if(userRepo.existsByEmail(request.getEmail())){
            return "User Already Exists With Email"+request.getEmail();
        }

        User user=new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        userRepo.save(user);
        return "User Registered Successfully";
    }

    public AuthResponse loginUser(String email, String password){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
        );
        User user=userRepo.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User Not Found with email :"+email));
        String token= jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    @Transactional
    public String deleteUser(String name){
        User user=userRepo.findByName(name)
                .orElseThrow(()->new UsernameNotFoundException("Username not Found"));
        userRepo.deleteByName(user.getName());
        return name;
    }
}
