package com.qhkj.zyzb.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role_perm_rel", catalog="zyzb",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"roleId", "permId"})})
@Getter @Setter
public class RolePermRel extends BaseModel {

    private static final long serialVersionUID = -4799091730332756697L;

    private Integer roleId;
    private Integer permId;
    
    public RolePermRel() {
        super();
    }
    
    public RolePermRel(Integer roleId, Integer permId) {
        super();
        this.roleId = roleId;
        this.permId = permId;
    }
}
