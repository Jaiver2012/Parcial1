package com.example.reto1.model.entity;

import java.io.Serializable;

public class Points implements Serializable {

    private String id;

    private int points;

    public Points() {
    }

    public Points(String id, int points) {
        this.id = id;
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public int getPoints() {
        return points;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
