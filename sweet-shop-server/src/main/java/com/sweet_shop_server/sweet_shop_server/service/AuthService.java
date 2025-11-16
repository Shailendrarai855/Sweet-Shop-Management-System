package com.sweet_shop_server.sweet_shop_server.service;

import com.sweet_shop_server.sweet_shop_server.dto.LoginRequest;
import com.sweet_shop_server.sweet_shop_server.dto.LoginResponse;
import com.sweet_shop_server.sweet_shop_server.dto.UserDTO;
import com.sweet_shop_server.sweet_shop_server.entity.User;
import com.sweet_shop_server.sweet_shop_server.entity.enumm.Role;
import com.sweet_shop_server.sweet_shop_server.exceptions.ResourceNotFoundException;
import com.sweet_shop_server.sweet_shop_server.repository.UserRepository;
import com.sweet_shop_server.sweet_shop_server.security.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.net.Authenticator;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    public final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public String register(UserDTO userDTO){
        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new RuntimeException("Email already exist.");
        }

        User user = modelMapper.map(userDTO, User.class);

        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
        log.info("User created with email : {}", user.getEmail());
        return "User Registered successfully!";
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        log.info("User logged in with id : "+user.getId());
        return new LoginResponse(accessToken, refreshToken);
    }


    public String refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found " +
                "with id: "+userId));

        return jwtService.generateAccessToken(user);
    }
}
