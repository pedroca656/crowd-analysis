package com.pucrs.controller;

import com.pucrs.parsing.Coords;
import com.pucrs.parsing.Parser;
import com.pucrs.parsing.Person;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Parser parser;

    private List<Person> personList;

    private List<Coords> nextCoordsList;

    private Bomb bomb;

    public Controller() {
        parser = new Parser();
        parser.parseFile();

        personList = Parser.personList;

        nextCoordsList = new ArrayList<>();
    }


}
