package com.pucrs.analysis;

import com.pucrs.parsing.DataPackage;
import com.pucrs.parsing.Person;
import com.pucrs.analysis.Line;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

public class Analyzer {
    private List<List<Person>> dataMatrix;
    private int totalFrames;
    private Float x1;
    private Float y1;
    private Float x2;
    private Float y2;

    private List<List<Person>> analysisMatrix;

    public Analyzer(DataPackage dataPackage) {
        this.dataMatrix = dataPackage.getDataMatrix();
        this.totalFrames = dataPackage.getTotalFrames();
        this.analysisMatrix = new ArrayList<List<Person>>();
    }

    // analyzes if there are pairs of people walking or standing still together
    public void findPairs() {
        double distance = 0;

        // creates new analysis matrix to receive results (matrix index = frame; list index = Pair)
        // list[i][j] -> [i] = frame number; [j] = amount of Pairs in that frame

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
//                            System.out.print("(Person " + j + " ,Person " + (g) + ")" );

                            // get people position
                            Float outerX = outerPerson.getX();
                            Float outerY = outerPerson.getY();
                            Float innerX = innerPerson.getX();
                            Float innerY = innerPerson.getY();

                            // calculate distance
                            distance = calculateDistance(outerX, innerX, outerY, innerY);
//                            System.out.println(" -> " + distance/100 + "m apart");

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
//        for (List<Person> list : analysisMatrix) {
//            for (int i = 0; i < list.size(); i++) {
//                for (int j = 0; j < list.size(); j++) {
//                    if (i != j) {
//
//                    }
//                }
//            }
//        }
//        print(analysisMatrix);
    }

    // analyzes if some person crossed paths with another
    public void findPeopleCrossingsPaths() {
        // se duas linhas se cruzarem = evento

        ArrayList<Line> allLines = new ArrayList<Line>();


        // all frames
        for (int frame = 0; frame < totalFrames; frame++) {
            for (int nextFrame = 1; nextFrame < totalFrames -1; nextFrame++) {
                // people
                for (int i = 0; i < dataMatrix.size() ; i++) {
                    Line personLine = new Line(new ArrayList<SegmentLine>());
                    Person currentPersonCurrentFrame = dataMatrix.get(i).get(frame);
                    Person currentPersonNextFrame = dataMatrix.get(i).get(nextFrame);

                    if (currentPersonCurrentFrame != null && currentPersonNextFrame != null ){
                        if (currentPersonCurrentFrame.getX() != currentPersonNextFrame.getX() || currentPersonCurrentFrame.getY() != currentPersonNextFrame.getY()) {
                            Point start = new Point(currentPersonCurrentFrame.getX(), currentPersonCurrentFrame.getY());
                            Point end = new Point(currentPersonNextFrame.getX(), currentPersonNextFrame.getY());
                            SegmentLine segment =  new SegmentLine(start, end);
                            personLine.addPiece(segment);
                        }
                    }
                    allLines.add(personLine);
                }
            }
        }

        for (int i = 0; i < allLines.size(); i++) {
            Line line = allLines.get(i);
            for (SegmentLine piece : line.getPieces()) {
                System.out.println("Start Point");
                System.out.println("| X = " +piece.getStart().getX());
                System.out.println("| Y = " +piece.getStart().getY());
                System.out.println("End Point");
                System.out.println("| X = " +piece.getEnd().getX());
                System.out.println("| X = " +piece.getEnd().getY());
                System.out.println("-------------------------------------------");
            }
        }

    }

    private double calculateDistance(Float x1, Float x2, Float y1, Float y2) {
        Float term1 = x2 - x1;
        Float term2 = y2 - y1;
        double expression = (pow(term1, 2) + pow(term2, 2));
        return round(sqrt(expression));
    }


    private boolean intersect(SegmentLine line1, SegmentLine line2) {
        double denominator = (line1.getStart().getX() - line1.getEnd().getX()) * (line2.getStart().getY() - line2.getEnd().getY())
                - (line1.getStart().getY() - line1.getEnd().getY()) * (line2.getStart().getX() - line2.getEnd().getX());
        double px = 0;
        double py = 0;

        if (denominator == 0) {
            System.out.println("Parallels");
            return  false;
        } else {

            px = ((line1.getStart().getX() * line1.getEnd().getY() - line1.getStart().getY() * line1.getEnd().getX())
                    * (line2.getStart().getX() - line2.getEnd().getX()) - (line1.getStart().getX() - line1.getEnd().getX())
                    * (line2.getStart().getX() * line2.getEnd().getY() - line2.getStart().getY() * line2.getEnd().getX()))
                    / denominator;

            py = ((line1.getStart().getX() * line1.getEnd().getY() - line1.getStart().getY() * line1.getEnd().getX())
                    * (line2.getStart().getY() - line2.getEnd().getY()) - (line1.getStart().getY() - line1.getEnd().getY())
                    * (line2.getStart().getX() * line2.getEnd().getY() - line2.getStart().getY() * line2.getEnd().getX()))
                    / denominator;

            System.out.println(px + "," + py);
            return true;
        }
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
