package com.vakhnenko.departments.entity;

public abstract class Entity {
    private int ID;
    private String name;

    public Entity(String name) {
        this.name = name;
        this.ID = getUniqeID();
    }

    public int getUniqeID() {
        return 0;
    }

    public int getID() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
