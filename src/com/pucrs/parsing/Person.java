package com.pucrs.parsing;

import com.pucrs.analysis.Relation;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private List<Float> x, y;
    private List<Relation> relations;
    private int id;

    public Person(List<Float> x, List<Float> y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public List<Float> getxCoords() { return x; }

    public List<Float> getyCoords() {
        return y;
    }

    public int getId() {
        return id;
    }
}
