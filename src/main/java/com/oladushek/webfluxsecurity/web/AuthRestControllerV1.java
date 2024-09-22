package com.oladushek.webfluxsecurity.web;

import com.oladushek.webfluxsecurity.web.dto.AuthRequestDto;
import com.oladushek.webfluxsecurity.web.dto.AuthResponseDto;
import com.oladushek.webfluxsecurity.web.dto.UserDto;
import com.oladushek.webfluxsecurity.entity.UserEntity;
import com.oladushek.webfluxsecurity.mapper.UserMapper;
import com.oladushek.webfluxsecurity.security.CustomPrincipal;
import com.oladushek.webfluxsecurity.security.SecurityService;
import com.oladushek.webfluxsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthRestControllerV1 {
    private final SecurityService securityService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public Mono<UserDto> register(@RequestBody UserDto dto) {
        UserEntity entity = userMapper.map(dto);
        return userService.registerUser(entity)
                .map(userMapper::map);
    }

    @PostMapping("/login")
    public Mono<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto){
        return securityService.authenticate(authRequestDto.getUsername(), authRequestDto.getPassword())
                .flatMap(tokenDetails -> Mono.just(
                        AuthResponseDto.builder()
                                .userId(tokenDetails.getUserId())
                                .token(tokenDetails.getToken())
                                .issuedAt(tokenDetails.getIssuedAt())
                                .expiresAt(tokenDetails.getExpiresAt())
                                .build()
                ));
    }

    @GetMapping("/info")
    public Mono<UserDto> getUserInfo(Authentication authentication){
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();

        return userService.getUserById(customPrincipal.getId())
                .map(userMapper::map);
    }
}
