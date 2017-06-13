package com.pucrs.parsing;

public class Coords {
    private Integer idOwner;
    private Float x;
    private Float y;

    public Coords(Float x, Float y) {
        this.x = x;
        this.y = y;
    }

    public void setIdOwner(Integer id) {
        this.idOwner = id;
    }

    public Integer getIdOwner() {
        return idOwner;
    }

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ") ";
    }
}
