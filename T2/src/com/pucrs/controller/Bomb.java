package com.pucrs.controller;

import com.pucrs.parsing.Coords;

public class Bomb {
    private Coords coords;
    private Float explosionRadius;
    private Float explosionDelay;

    public Bomb(Coords coords) {
        this.coords = coords;
    }
}
