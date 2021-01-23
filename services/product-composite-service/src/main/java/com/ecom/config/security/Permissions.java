package com.ecom.config.security;

import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.core.publisher.Mono;

@NoArgsConstructor
public class Permissions {
    public static Mono<UserPrincipal> getCurrentUser() {
        return ReactiveSecurityContextHolder.getContext()
                .map(s -> s.getAuthentication().getPrincipal())
                .cast(UserPrincipal.class);
    }

    public static Mono<Authentication> getCurrentAuthentication() {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> context.getAuthentication())
                .cast(Authentication.class);
    }
}
