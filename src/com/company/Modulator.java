package com.company;

import java.util.ArrayList;

public class Modulator {

    private ArrayList<Point> constellation;
    private Point currentSignal;

    private double E = 1;

    public Modulator() {
        double cos45 = Math.sqrt(2)/2;

        constellation = new ArrayList<>();
        constellation.add(new Point(-cos45, cos45, 0));
        constellation.add(new Point(cos45, -cos45, 1));
        constellation.add(new Point(-cos45, -cos45, 2));
        constellation.add(new Point(cos45, cos45, 3));
        constellation.add(new Point(0, -1, 4));
        constellation.add(new Point(0, 1, 5));
        constellation.add(new Point(-1, 0, 6));
        constellation.add(new Point(1, 0, 7));
    }

    public Point modulate(int data) {
        currentSignal = constellation.get(data);
        return currentSignal;
    }

    public Point getPointOfSignal(int num) {
        return constellation.get(num);
    }
}
