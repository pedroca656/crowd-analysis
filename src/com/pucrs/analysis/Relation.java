package com.pucrs.analysis;

import com.pucrs.parsing.Person;

public class Relation {
    private Person first, second;
    private Float weight;

    public Relation(Person first, Person second, Float weight) {
        this.first = first;
        this.second = second;
        this.weight = weight;
    }

    public Person getFirst() {
        return first;
    }

    public Person getSecond() {
        return second;
    }

    public Float getWeight() {
        return weight;
    }
}
