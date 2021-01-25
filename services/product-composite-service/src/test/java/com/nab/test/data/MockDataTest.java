package com.nab.test.data;

import com.nab.infrastructure.config.security.UserPrincipal;
import java.util.Set;
import java.util.stream.Collectors;
import org.assertj.core.util.Sets;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


public class MockDataTest {

    private static final String USER_NAME = "customer";

    private static final Set<GrantedAuthority> authorities =
        Set.of("SCOPE_product:read")
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());


    public static final UsernamePasswordAuthenticationToken AUTH =
        new UsernamePasswordAuthenticationToken(
            UserPrincipal.builder()
                .username(USER_NAME)
                .build(),
            "token",
            authorities);


    public static final UsernamePasswordAuthenticationToken WITHOUT_AUTH =
        new UsernamePasswordAuthenticationToken(
            UserPrincipal.builder()
                .username(USER_NAME)
                .build(),
            "token",
            Sets.newHashSet());


}
