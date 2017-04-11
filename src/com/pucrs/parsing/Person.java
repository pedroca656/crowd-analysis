package com.pucrs.parsing;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private Float x, y;
    private int id;

    private List<Person> relatedPeople;

    public Person(Float x, Float y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.relatedPeople = new ArrayList<Person>();
    }

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Person> getRelatedPeople() {
        return (ArrayList<Person>) relatedPeople;
    }
}
