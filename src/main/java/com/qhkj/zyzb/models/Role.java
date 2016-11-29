package com.qhkj.zyzb.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role", catalog="zyzb")
@Getter @Setter
public class Role extends BaseModel {

    private static final long serialVersionUID = -7615873557510519129L;
    
    @Column(unique=true)
    private String name;

    public Role() {
        super();
    }
    
    public Role(String name) {
        super();
        this.name = name;
    }
}
