package com.pucrs.analysis;

import com.pucrs.parsing.DataPackage;
import com.pucrs.parsing.Person;
import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;
import java.util.HashMap;
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
    public List<Relation> findPairs() {
        float distance = 0;

        // prepare list
        List<List<Relation>> analysisMatrix = new ArrayList<List<Relation>>();
        for (int i = 0; i < totalFrames; i++) {
            analysisMatrix.add(new ArrayList<Relation>());
        }

        // create lists for the analysis
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

                            // if distance less than 1.0m -> add to list
                            if (distance / 100 < 1.00) {
                                //System.out.print("Frame " + i + "(Person " + j + ", Person " + g + ")");
                                //System.out.println(" -> " + distance / 100 + "m apart");
                                analysisMatrix.get(i).add(new Relation(dataMatrix.get(j), dataMatrix.get(g), null));
                            }
                        }
                    }
                }
            }
        }
        // overwrite analysisData with cleaned list
        analysisMatrix = prepareFindPairs(analysisMatrix);

        // count the event occurances in each frame from every person to take the avarage number of events
        HashMap<Relation, Float> analysisAvarage = new HashMap<Relation, Float>();
        for (int i = 0; i < analysisMatrix.size(); i++) {
            for (int j = 0; j < analysisMatrix.get(i).size(); j++) {
                if (!analysisAvarage.containsKey(analysisMatrix.get(i).get(j))) {
                    analysisAvarage.put(analysisMatrix.get(i).get(j), 0f);
                } else {
                    analysisAvarage.put(analysisMatrix.get(i).get(j) ,analysisAvarage.get(analysisMatrix.get(i).get(j))+1);
                }
            }
        }

        // add the avarage to each Relation
        for (int i = 0; i < analysisMatrix.size(); i++) {
            for (int j = 0; j < analysisMatrix.get(i).size(); j++) {
                Float avarage =  (analysisAvarage.get(analysisMatrix.get(i).get(j))*100)/analysisMatrix.get(i).get(j).getFirst().getxCoords().size();
                analysisMatrix.get(i).get(j).setWeight(avarage);
            }
        }

        // crunch the relations into one list
        List<Relation> analysisData = new ArrayList<Relation>();
        for (int i = 0; i < analysisMatrix.size(); i++) {
            for (int j = 0; j < analysisMatrix.get(i).size(); j++) {
                if (!analysisData.contains(analysisMatrix.get(i).get(j))) {
                    analysisData.add(analysisMatrix.get(i).get(j));
                }
            }
        }
        return analysisData;
    }

    private List<List<Relation>> prepareFindPairs(List<List<Relation>> analysisMatrix) {
        // search analysisMatrix for duplicates
        for (int i = 0; i < totalFrames; i++) {
            for (int j = 0; j < analysisMatrix.get(i).size(); j++) {
                if (analysisMatrix.get(i).get(j) != null) {
                    analysisMatrix.get(i).remove(new Relation(analysisMatrix.get(i).get(j).getSecond(), analysisMatrix.get(i).get(j).getFirst(), null));
                }
            }
        }
        // with the list clean from duplicates, create new list without gaps
        List<List<Relation>> returnList = new ArrayList<List<Relation>>();
        for (int i = 0; i < totalFrames; i++) {
            returnList.add(new ArrayList<Relation>());
        }
        for (int i = 0; i < analysisMatrix.size(); i++) {
            for (int j = 0; j < analysisMatrix.get(i).size(); j++) {
                if (analysisMatrix.get(i).get(j) != null) {
                    returnList.get(i).add(analysisMatrix.get(i).get(j));
                }
            }
        }
        return returnList;
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
    public void printPairDetection() {
        List<Relation> analysisData = findPairs();
        for (int i=0; i < analysisData.size(); i++) {
            System.out.println("Avarage between (" + analysisData.get(i).getFirst().getId() + ", " + analysisData.get(i).getSecond().getId() + "): " + analysisData.get(i).getWeight() + "%");
        }
    }
}
