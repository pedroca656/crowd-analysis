package com.pucrs.analysis;

import com.pucrs.parsing.DataPackage;
import com.pucrs.parsing.Person;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

public class Analyzer {
    private List<Person> dataMatrix;
    private int totalFrames;

    public Analyzer(DataPackage dataPackage) {
        this.dataMatrix = dataPackage.getDataMatrix();
        this.totalFrames = dataPackage.getTotalFrames();
    }

    // analyzes if there are pairs of people walking or standing still together
    public void findPairs() {
        float distance = 0;
        List<List<Relation>> analysisMatrix = new ArrayList<List<Relation>>();
        for (int i = 0; i < totalFrames; i++) {
            analysisMatrix.add(new ArrayList<Relation>());
        }

        List<Person> tempList = new ArrayList<Person>();
        List<Float> firstXCoordsList, firstYCoordsList, secondXCoordsList, secondYCoordsList;

        // for all people in the list
        for (int j = 0; j < dataMatrix.size(); j++) {
            // test them against all *other* people in the list
            for (int g = 0; g < dataMatrix.size(); g++) {
                // for all frames
                for (int i = 0; i < totalFrames; i++) {
                    // if not comparing a person with itself
                    if (g != j) {
                        // get data
                        firstXCoordsList = dataMatrix.get(j).getxCoords();
                        firstYCoordsList = dataMatrix.get(j).getyCoords();
                        secondXCoordsList = dataMatrix.get(g).getxCoords();
                        secondYCoordsList = dataMatrix.get(g).getyCoords();

                        // if data is not null
                        if ((firstXCoordsList.get(i) != null) && (secondXCoordsList.get(i) != null)) {

                            // get people position
                            Float firstX = firstXCoordsList.get(i);
                            Float firstY = firstYCoordsList.get(i);
                            Float secondX = secondXCoordsList.get(i);
                            Float secondY = secondYCoordsList.get(i);

                            // calculate distance
                            distance = calculateDistance(firstX, secondX, firstY, secondY);

                            // if people less than 1.2m distant -> add to list
                            if (distance / 100 < 1.00) {
                                System.out.print("Frame " + i + "(Person " + j + ", Person " + g + ")");
                                System.out.println(" -> " + distance / 100 + "m apart");
                                analysisMatrix.get(i).add(new Relation(dataMatrix.get(j), dataMatrix.get(g), distance));
                            }
                        }
                    }
                }
            }
        }
        // print(analysisMatrix);
    }

    // analyzes if some person crossed paths with another
    public void findCrossings() {
        // se duas linhas se cruzarem = evento
        //
    }

    private float calculateDistance(Float x1, Float x2, Float y1, Float y2) {
        Float term1 = x2 - x1;
        Float term2 = y2 - y1;
        double expression = (pow(term1, 2) + pow(term2, 2));
        return round(sqrt(expression));
    }

    // prints pairs detected by frame
//    private void print(List<List<Person>> analysisMatrix) {
//        for (int i=0; i < analysisMatrix.size(); i++) {
//            System.out.println("Frame " + i + " --------------------");
//            for (int j=0; j < analysisMatrix.get(i).size(); j++) {
//                for (int g = 0; g < analysisMatrix.get(i).get(j).getRelatedPeople().size(); g++) {
//                    System.out.println("Pair " + analysisMatrix.get(i).get(j).getId() + " -> " + analysisMatrix.get(i).get(j).getRelatedPeople().get(g).getId() + ": " + calculateDistance(analysisMatrix.get(i).get(j).getX(), analysisMatrix.get(i).get(j).getRelatedPeople().get(g).getX(), analysisMatrix.get(i).get(j).getY(), analysisMatrix.get(i).get(j).getRelatedPeople().get(g).getY()) + "cm");
//                }
//            }
//        }
//    }
}
