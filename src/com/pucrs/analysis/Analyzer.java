package com.pucrs.analysis;

import com.pucrs.parsing.PeopleCoordinate;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

public class Analyzer {
    private List<List<PeopleCoordinate>> peopleMatrix;

    public Analyzer(List<List<PeopleCoordinate>> peopleMatrix) {
        this.peopleMatrix = peopleMatrix;
    }

    // analyzes if there are pairs of people walking or standing still together
    public void findPairs() {

        List<List<PeopleCoordinate>> analysisMatrix = new ArrayList<>();
        List<PeopleCoordinate> closePeople = new ArrayList<>();
        // cópia da peopleMatrix com listas de Pessoas dentro (se no frame uma pessoa estava a X de distancia de
        // outra pessoa, adicona seu companheiro nessa lista. Se ao menos em Y% dos frames totais existentes eles estao
        // um na lista do outro, contar como um par (evento))

        int personsIndex = peopleMatrix.size();

        // compara a distancia entre todos da matriz
        for (int i = 0; i < personsIndex; i++) {
            List<PeopleCoordinate> peopleCoordinates = peopleMatrix.get(i);
            for (int j = 1; j < personsIndex -1 ; j++) {
                System.out.print("Person = " + i + " and Person = " + j );

                List<PeopleCoordinate> innerPeopleCoordinates = peopleMatrix.get(j);
                PeopleCoordinate externalPeopleCoordinate = peopleCoordinates.get(i);
                PeopleCoordinate innerPeopleCoordinate = innerPeopleCoordinates.get(j);

                Float externalX = externalPeopleCoordinate.getX();
                Float externalY = externalPeopleCoordinate.getY();

                Float innerX = innerPeopleCoordinate.getX();
                Float innerY = innerPeopleCoordinate.getY();

                double distance = calculateDistance(externalX, innerX, externalY, innerY);
                System.out.println(" they are close " + distance + " meters");

                if (distance < 2.0) {
                    closePeople.add(innerPeopleCoordinate);
                    closePeople.add(externalPeopleCoordinate);
                    analysisMatrix.add(closePeople);
                }
            }
        }

//        for (int i=0; i < personsIndex; i++) {
//            List<PeopleCoordinate> tempList = analysisMatrix.get(i);
//            System.out.println("\n|--Person "  + i + ":  ");
//            for (int j=0; j < tempList.size(); j++) {
//                if (tempList.get(j) != null) {
//                    PeopleCoordinate tempPerson = tempList.get(j);
//                    System.out.println("| frame " + j + ": (" + tempPerson.getX() + "," + tempPerson.getY() + ") ");
//                } else {
//                    System.out.println("| frame " + j + ": (null)");
//                }
//            }
//        }

    }

    // analyzes if some person crossed paths with another
    public void findCrossings() {
        // se duas linhas se cruzarem = evento
        //
    }

    private double calculateDistance(Float x1, Float x2, Float y1, Float y2) {
        Float term1 = x2 - x1;
        Float term2 = y2 - y1;
        double expression = (pow(term1, 2) + pow(term2, 2));
        return round(sqrt(expression));
    }
}
