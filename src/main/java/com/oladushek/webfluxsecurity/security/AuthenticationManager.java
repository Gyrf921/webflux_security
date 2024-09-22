package com.oladushek.webfluxsecurity.security;

import com.oladushek.webfluxsecurity.entity.UserEntity;
import com.oladushek.webfluxsecurity.exception.UnauthorizedException;
import com.oladushek.webfluxsecurity.repository.UserRepository;
import com.oladushek.webfluxsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final UserService userService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();

        return userService.getUserById(principal.getId())
                .filter(UserEntity::getEnabled)
                .switchIfEmpty(Mono.error(new UnauthorizedException("User disable")))
                .map(user -> authentication);
    }
}
