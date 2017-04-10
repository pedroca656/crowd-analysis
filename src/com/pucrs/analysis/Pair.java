package com.pucrs.analysis;

import com.pucrs.parsing.PeopleCoordinate;

public class Pair {
    private PeopleCoordinate first;
    private PeopleCoordinate second;

    public Pair(PeopleCoordinate first, PeopleCoordinate second) {
        this.first = first;
        this.second = second;
    }

    public PeopleCoordinate getFirst() {
        return first;
    }

    public PeopleCoordinate getSencond()
    {
        return second;
    }
}
