package com.vakhnenko.departments.entity;

public abstract class Entity {
    private int ID;
    private String name;
    private static int uniqueID;

    public Entity(String name) {
        this.name = name;
        this.ID = uniqueID++;
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
