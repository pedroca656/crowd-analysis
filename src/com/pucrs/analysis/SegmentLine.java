package com.pucrs.analysis;

/**
 * Created by wos on 11/04/17.
 */
public class SegmentLine {

    private Point start;
    private Point end;

    public SegmentLine(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }
}
