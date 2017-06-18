package com.pucrs;

import com.pucrs.controller.Controller;
import com.pucrs.parsing.Parser;
import com.pucrs.viewer.ViewTwo;

public class Main {

    public static void main(String[] args) {
        new Controller();

        new ViewTwo();
        //new View();





        // OPTIONAL:
        // print personList content
        Parser.printPersonList();
    }
}
