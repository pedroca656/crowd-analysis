package com.pucrs.analysis;

import java.util.List;

/**
 * Created by wos on 11/04/17.
 */
public class Line {

    private List<SegmentLine> pieces;

    public Line(List<SegmentLine> pieces) {
        this.pieces = pieces;
    }

    public List<SegmentLine> getPieces() {
        return pieces;
    }

    public void addPiece(SegmentLine piece) {
        pieces.add(piece);
    }
}
