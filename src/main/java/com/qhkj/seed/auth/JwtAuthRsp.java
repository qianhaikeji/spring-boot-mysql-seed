package com.qhkj.seed.auth;

import java.io.Serializable;
import java.util.List;

import com.qhkj.seed.models.Permission;

import lombok.Getter;

@Getter
public class JwtAuthRsp implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String username;
    private final String token;
    private final String role;
    private final List<Permission> perms;

    public JwtAuthRsp(String username, String token, String role, List<Permission> perms) {
    	this.username = username;
        this.token = token;
        this.role = role;
        this.perms = perms;
    }

}
