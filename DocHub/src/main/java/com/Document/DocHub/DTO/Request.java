package com.Document.DocHub.DTO;

import com.Document.DocHub.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Request {
    private String name;
    private String email;
    private String password;
    private Role role;

}
