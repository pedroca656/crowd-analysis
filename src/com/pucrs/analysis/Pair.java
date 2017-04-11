package com.pucrs.analysis;

import com.pucrs.parsing.Person;

public class Pair {
    private Person first;
    private Person second;

    public Pair(Person first, Person second) {
        this.first = first;
        this.second = second;
    }

    public Person getFirst() {
        return first;
    }

    public Person getSencond()
    {
        return second;
    }
}
