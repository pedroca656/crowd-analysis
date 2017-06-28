package com.pucrs.parsing;

import java.util.List;

public class Pessoa {
    private List<Cordenadas> cordenadasList;

    private Cordenadas currentCoord;

    private Integer nextCoordIndex;

    private Integer id;

    Boolean reverseRoute;

    public Pessoa(List<Cordenadas> cordenadasList, Integer id) {
        nextCoordIndex = 0;
        currentCoord = cordenadasList.get(0);
        reverseRoute = false;
        this.cordenadasList = cordenadasList;
        this.id = id;
    }

    public Cordenadas getNextCoord() {
        if (reverseRoute) {
            if (nextCoordIndex == 0) {
                reverseRoute = false;
                currentCoord = cordenadasList.get(nextCoordIndex+1);
                return cordenadasList.get(nextCoordIndex++);
            }
            currentCoord = cordenadasList.get(nextCoordIndex-1);
            return cordenadasList.get(nextCoordIndex--);
        }
        else {
            if (nextCoordIndex == cordenadasList.size()-1) {
                reverseRoute = true;
                currentCoord = cordenadasList.get(nextCoordIndex-1);
                return cordenadasList.get(nextCoordIndex--);
            }
            currentCoord = cordenadasList.get(nextCoordIndex+1);
            return cordenadasList.get(nextCoordIndex++);
        }
    }

    public Cordenadas getCurrentCoord() {
        return currentCoord;
    }

    public void setCurrentCoord(Cordenadas currentCoord) {
        this.currentCoord = currentCoord;
    }

    public List<Cordenadas> getCordenadasList() {
        return cordenadasList;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return "Pessoa " + id + ": " + cordenadasList;
    }
}
