package com.pucrs;

import com.pucrs.parsing.Parser;
import com.pucrs.viewer.View;

public class Main {

    public static void main(String[] args) {
        Parser p = new Parser();
        p.parseFileToMatrix();
        p.print();

        //TODO: Fixed open a window and draw a triangle
        new View(p.getPeopleMatrix(), p.getTotalFrames(), p.getPixelsToMeters());
    }
}
