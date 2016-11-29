package com.qhkj.zyzb.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class BaseModel implements Comparable<BaseModel>, Serializable {

	private static final long serialVersionUID = -8052356705853638238L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(nullable = true)
    private Date createdAt;

    @Column(nullable = true)
    private Date updatedAt;

    @PrePersist
    public void prePersist(){
        createdAt = updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate(){
        updatedAt = new Date();
    }

    @Override
    public int compareTo(BaseModel o) {
        return this.getId().compareTo(o.getId());
    }

    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass())
            return false;

        return this.getId().equals(((BaseModel) other).getId());
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer _id) {
        id = _id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}