package com.qhkj.seed.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "admin", catalog="seed")
@Getter @Setter
public class Admin extends BaseModel{
    private static final long serialVersionUID = 3381508634775899711L;
    
    @Column(unique=true)
    private String username;
    @JsonProperty(access=Access.WRITE_ONLY)
    private String password;
    private String email;
    private String phone;
    private Integer roleId;
    private Date lastTime;
    
    public Admin() {
        super();
    }
    
    public Admin(String username, String email, String phone, String password, Integer roleId) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.roleId = roleId;
    }
    
}
