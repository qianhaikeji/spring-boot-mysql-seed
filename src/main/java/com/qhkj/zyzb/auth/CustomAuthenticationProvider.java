package com.qhkj.zyzb.auth;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.qhkj.zyzb.models.Admin;
import com.qhkj.zyzb.services.AdminService;

/**
 * 自定义登录验证方法
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired AdminService adminService;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String)authentication.getPrincipal();
        
        String loginType = (String) authentication.getDetails();
        if ("admin".equals(loginType)){
            System.out.println("admin");
            UserDetails userDetails = adminService.loadUserByUsername(username);
            
            if (userDetails == null) {
                System.out.println("user is null");
                throw new BadCredentialsException(String.format("用户 '%s' 不存在.", username));
            }
            
            // System.out.println("match==="+passwordEncoder.matches((String)authentication.getCredentials(), user.getPassword()));
            if (passwordEncoder.matches((String)authentication.getCredentials(), userDetails.getPassword())){
//                return new UsernamePasswordAuthenticationToken(AuthUserFactory.create(user), null, Collections.singleton(AuthUserFactory.createAuthority(user)));
                
                return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
            } else {
                throw new BadCredentialsException("密码错误！");
            }
        } else if ("client".equals(loginType)){
            System.out.println("client");
            throw new BadCredentialsException(String.format("无效的用户 '%s'.", username));
        } else {
            System.out.println("xxx");
            throw new BadCredentialsException("非法入口登录！"); 
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
    
}