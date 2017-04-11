package com.pucrs.analysis;

import com.pucrs.parsing.DataPackage;
import com.pucrs.parsing.Person;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

public class Analyzer {
    private List<List<Person>> dataMatrix;
    private int totalFrames;

    public Analyzer(DataPackage dataPackage) {
        this.dataMatrix = dataPackage.getDataMatrix();
        this.totalFrames = dataPackage.getTotalFrames();
    }

    // analyzes if there are pairs of people walking or standing still together
    public void findPairs() {
        double distance = 0;

        // creates new analysis matrix to receive results (matrix index = frame; list index = Pair)
        // list[i][j] -> [i] = frame number; [j] = amount of Pairs in that frame
        List<List<Person>> analysisMatrix = new ArrayList<List<Person>>();
        // set up list
        for (int i = 0; i < totalFrames; i++) {
            analysisMatrix.add(new ArrayList<Person>());
        }

        // for all frames
        for (int i = 0; i < totalFrames; i++) {
            // create an inner list to hold the pairs
            // for all people in the list
            for (int j = 0; j < dataMatrix.size(); j++) {
                // test them against all *other* people in the list
                for (int g = 0; g < dataMatrix.size(); g++) {
                    // if not comparing a person with itself
                    if (g != j) {
                        // get data
                        Person outerPerson = dataMatrix.get(j).get(i);
                        Person innerPerson = dataMatrix.get(g).get(i);

                        // if data is not null
                        if ((outerPerson != null) && (innerPerson != null)) {
                            System.out.print("(Person " + j + " ,Person " + (g) + ")" );

                            // get people position
                            Float outerX = outerPerson.getX();
                            Float outerY = outerPerson.getY();
                            Float innerX = innerPerson.getX();
                            Float innerY = innerPerson.getY();

                            // calculate distance
                            distance = calculateDistance(outerX, innerX, outerY, innerY);
                            System.out.println(" -> " + distance/100 + "m apart");

                            // if people less than 2.0m distant -> add to list of Pairs in frame
                            if (distance/100 < 1.20) {
                                outerPerson.getRelatedPeople().add(innerPerson);
                                analysisMatrix.get(i).add(outerPerson); // i = frame
                            }
                        }
                    }
                }
            }
        }
        for (List<Person> list : analysisMatrix) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < list.size(); j++) {
                    if (i != j) {

                    }
                }
            }
        }
        print(analysisMatrix);
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

    // prints pairs detected by frame
    private void print(List<List<Person>> analysisMatrix) {
        for (int i=0; i < analysisMatrix.size(); i++) {
            System.out.println("Frame " + i + " --------------------");
            for (int j=0; j < analysisMatrix.get(i).size(); j++) {
                for (int g = 0; g < analysisMatrix.get(i).get(j).getRelatedPeople().size(); g++) {
                    System.out.println("Pair " + analysisMatrix.get(i).get(j).getId() + " -> " + analysisMatrix.get(i).get(j).getRelatedPeople().get(g).getId() + ": " + calculateDistance(analysisMatrix.get(i).get(j).getX(), analysisMatrix.get(i).get(j).getRelatedPeople().get(g).getX(), analysisMatrix.get(i).get(j).getY(), analysisMatrix.get(i).get(j).getRelatedPeople().get(g).getY()) + "cm");
                }
            }
        }
    }
}
