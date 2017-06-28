package com.pucrs;

import com.pucrs.parsing.Parser;
import com.pucrs.viewer.ViewTwo;

public class Main {

    public static void main(String[] args) {
        Parser parser = new Parser();
        parser.parseFile();

        new ViewTwo();

        // OPTIONAL:
        // print personList content
        Parser.printPersonList();
    }
}
