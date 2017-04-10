package com.pucrs.analysis;

import com.pucrs.parsing.PeopleCoordinate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

public class Analyzer {
    private List<List<PeopleCoordinate>> peopleMatrix;
    private int totalFrames;

    public Analyzer(List<List<PeopleCoordinate>> peopleMatrix, int totalFrames) {
        this.peopleMatrix = peopleMatrix;
        this.totalFrames = totalFrames;
    }

    // analyzes if there are pairs of people walking or standing still together
    public void findPairs() {
        int frameIndex = 0;
        double distance = 0;

        // creates new analysis matrix to receive results (matrix index = frame; list index = Pair)
        // list[i][j] -> [i] = frame number; [j] = amount of Pairs in that frame
        List<List<Pair>> analysisMatrix = new ArrayList<>();

        // for all frames
        while (frameIndex < totalFrames) {
            // create an inner list to hold the pairs
            analysisMatrix.add(frameIndex, new ArrayList<Pair>());
            // for all people in the list
            for (int j = 0; j < peopleMatrix.size(); j++) {
                // test them against all *other* people in the list
                for (int g = 0; g < peopleMatrix.size(); g++) {
                    // if not comparing a person with itself
                    if (g != j) {
                        // get data
                        PeopleCoordinate first = peopleMatrix.get(j).get(frameIndex);
                        PeopleCoordinate second = peopleMatrix.get(g).get(frameIndex);

                        // if data is not null
                        if ((first != null) && (second != null)) {
                            System.out.print("(Person " + j + " ,Person " + (g) + ")" );

                            // get people position
                            Float firstX = first.getX();
                            Float firstY = first.getY();
                            Float secondX = second.getX();
                            Float secondY = second.getY();

                            // calculate distance
                            distance = calculateDistance(firstX, secondX, firstY, secondY);
                            System.out.println(" -> " + distance + " m apart");

                            // if people less than 2.0m distant -> add to list of Pairs in frame
                            if (distance < 2.0) {
                                analysisMatrix.get(frameIndex).add(new Pair(first, second)); // i = frame
                            }
                        }
                    }
                }
            }
            frameIndex++;
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
    private void print(List<List<Pair>> analysisMatrix) {
        for (int i=0; i < analysisMatrix.size(); i++) {
            System.out.println("Frame " + i + " --------------------");
            for (int j=0; j < analysisMatrix.get(i).size(); j++) {
                System.out.println("Pair found: " + analysisMatrix.get(i).get(j).getFirst().getNumber() + " and " + analysisMatrix.get(i).get(j).getSencond().getNumber());
            }

        }
    }
}
