package com.pucrs;

import com.pucrs.analysis.Analyzer;
import com.pucrs.parsing.Parser;
import com.pucrs.viewer.View;

public class Main {

    public static void main(String[] args) {
        // parse file
        Parser p = new Parser();
        p.parseFile();
//        p.print();

        // analyze file
        Analyzer a = new Analyzer(p.getDataPackage());
        a.printPairDetection();

        // create view
        new View(p.getDataPackage());
    }
}
