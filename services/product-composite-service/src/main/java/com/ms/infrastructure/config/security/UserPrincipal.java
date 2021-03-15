package com.ms.infrastructure.config.security;

import java.util.Set;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@Builder
public class UserPrincipal {
    private String username;
    private Set<GrantedAuthority> authorities;
}
