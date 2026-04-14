package com.example.math_race.entities;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class BaseEntity {
    private UUID id;
    private Date creationDate;
    private Date updatedDate;
    private boolean deleted;
    private Date deletionDate;

    public BaseEntity() {
        deleted = false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @PrePersist
    protected void onCreate() {
        this.creationDate = new Date();
        this.updatedDate = this.creationDate;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = new Date();
    }
}