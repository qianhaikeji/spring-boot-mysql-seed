package com.qhkj.seed.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 7468440280189473904L;

	private final String username;
	private final String password;
	private final Collection<? extends GrantedAuthority> authorities;

	public CustomUserDetails(String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
	@Override
	public String getUsername() {
		return username;
	}
	
	@JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
