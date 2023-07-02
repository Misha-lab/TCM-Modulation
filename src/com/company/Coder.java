package com.company;

public class Coder {
    private int state;
    public static final int STATES_COUNT = 4;

    public int encode(int data) {
        int second = state & 1;
        int first = (state >> 1) & 1;
        //System.out.println("stateBefore = " + first + "" + second);
        int u0 = (data >> 1) & 1;
        int u1 = data & 1;
        //System.out.println("U = " + u0 + "" + u1);
        int v0 = first;
        int v1 = second ^ u0;
        int v2 = u1;
        //System.out.println("V = " + v0 + "" + v1 + "" + v2);
        second = first;
        first = u0;
        state = (first << 1) | (second);
        //System.out.println("stateAfter = " + first + "" + second);
        int v = (v0 << 2) | (v1 << 1) | (v2);
        return v;
    }

    public static Point encode(int state, int data) {
        int second = state & 1;
        int first = (state >> 1) & 1;
        //System.out.println("stateBefore = " + first + "" + second);
        int u0 = (data >> 1) & 1;
        int u1 = data & 1;
        //System.out.println("U = " + u0 + "" + u1);
        int v0 = first;
        int v1 = second ^ u0;
        int v2 = u1;
        //System.out.println("V = " + v0 + "" + v1 + "" + v2);
        second = first;
        first = u0;
        state = (first << 1) | (second);
        //System.out.println("stateAfter = " + first + "" + second);
        int v = (v0 << 2) | (v1 << 1) | (v2);
        return new Point(v, state);
    }

    public int getState() {
        return state;
    }
}
