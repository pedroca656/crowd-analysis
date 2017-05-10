package com.pucrs.parsing;

import java.util.ArrayList;
import java.util.List;

public class DataPackage {
    private List<Person> dataMatrix = new ArrayList<>();

    private Integer maxWidth = 0;
    private Integer maxHeight = 0;

    private int totalFrames = 0;

    public DataPackage(List<Person> dataMatrix, int maxWidth, int maxHeight, int totalFrames) {
        this.dataMatrix = dataMatrix;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.totalFrames = totalFrames;
    }

    public List<Person> getDataMatrix() {
        return dataMatrix;
    }

    public Integer getMaxWidth() {
        return maxWidth;
    }

    public Integer getMaxHeight() {
        return maxHeight;
    }

    public int getTotalFrames() {
        return totalFrames;
    }
}
