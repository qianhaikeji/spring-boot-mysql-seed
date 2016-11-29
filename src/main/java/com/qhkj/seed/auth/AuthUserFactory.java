package com.qhkj.seed.auth;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.qhkj.seed.models.Admin;

public final class AuthUserFactory {

    private AuthUserFactory() {
    }

//    public static CustomUserDetails create(Admin user) {
//        return new CustomUserDetails(
//                user.getId(),
//                user.getUsername(),
//                user.getEmail(),
//                user.getPassword(),
//                Collections.singleton(createAuthority(user))
//                //mapToGrantedAuthorities(user.getAuthorities())
//        );
//    }
//    
////    private static List<GrantedAuthority> mapToGrantedAuthorities(Authority authorities) {
////        return authorities == null ? Collections.emptyList() : authorities.stream()
////                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
////                .collect(Collectors.toList());
////    }
//    
//    public static GrantedAuthority createAuthority(Admin user) {
//        return new SimpleGrantedAuthority(user.getRole());
//    }
}