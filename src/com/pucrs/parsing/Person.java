package com.pucrs.parsing;

public class Person {
    private Float x, y;
    private int number;

    public Person(Float x, Float y, int number) {
        this.x = x;
        this.y = y;
        this.number = number;
    }

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    public int getNumber() {
        return number;
    }
}
