package com.company;

public class Point implements Cloneable {
    private double x;
    private double y;
    private int data;

    public Point() {

    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(double x, double y, int data) {
        this.x = x;
        this.y = y;
        this.data = data;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getData() {
        return data;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setData(int data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Signal №" + data + ": [" + x + "; " + y + "]";
    }
}
