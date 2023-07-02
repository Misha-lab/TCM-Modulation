package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Modulator {

    private List<Point> constellation;
    private Point currentSignal;

    private double E = 1;

    public Modulator() {
        double cos45 = Math.sqrt(2)/2;

        constellation = new ArrayList<>(List.of(
                new Point(-cos45, cos45, 0),
                new Point(cos45, -cos45, 1),
                new Point(-cos45, -cos45, 2),
                new Point(cos45, cos45, 3),
                new Point(0, -1, 4),
                new Point(0, 1, 5),
                new Point(-1, 0, 6),
                new Point(1, 0, 7)));
    }

    public Point modulate(int data) {
        currentSignal = constellation.get(data);
        return currentSignal;
    }

    public Point getPointOfSignal(int num) {
        return constellation.get(num);
    }
}
