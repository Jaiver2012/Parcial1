package com.example.reto1.model.entity;

import java.io.Serializable;

public class ItemList implements Serializable {

    private String name;
    private int value;

    public ItemList() {
    }

    public ItemList(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
