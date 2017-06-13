package com.pucrs;

import com.pucrs.controller.Controller;
import com.pucrs.parsing.Parser;

public class Main {

    public static void main(String[] args) {
        new Controller();

        // OPTIONAL:
        // print personList content
        Parser.printPersonList();
    }
}
