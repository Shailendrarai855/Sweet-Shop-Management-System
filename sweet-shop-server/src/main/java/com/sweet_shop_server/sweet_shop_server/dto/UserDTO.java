package com.sweet_shop_server.sweet_shop_server.dto;

import com.sweet_shop_server.sweet_shop_server.entity.enumm.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    private String name;
    private String email;
    private String password;
    private Role role;
    private LocalDateTime createdAt;
}
