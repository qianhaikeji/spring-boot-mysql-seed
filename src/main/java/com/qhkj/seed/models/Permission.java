package com.qhkj.seed.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "permission", catalog="seed")
@Getter @Setter
public class Permission extends BaseModel {

    private static final long serialVersionUID = -4906401427885379394L;
    
    private String name;
    private String perm;
    
    public Permission() {
        super();
    }

    public Permission(String name, String perm) {
        super();
        this.name = name;
        this.perm = perm;
    }
}
