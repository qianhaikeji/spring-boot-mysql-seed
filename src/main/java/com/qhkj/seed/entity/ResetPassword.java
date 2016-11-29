package com.qhkj.seed.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResetPassword {
    private String oldPassword;
    private String newPassword;
    
    public ResetPassword() {

    }

    public ResetPassword(String oldPassword, String newPassword) {
        super();
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
