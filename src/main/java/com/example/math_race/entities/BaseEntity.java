package com.example.math_race.entities;

import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Data
public abstract class BaseEntity {
    private int id;
    private Date creationDate;
    private Date updatedDate;
    private boolean deleted;
    private Date deletionDate;

    public BaseEntity() {
        creationDate = new Date();
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
}