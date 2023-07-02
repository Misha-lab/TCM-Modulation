package com.company;

public class Edge implements Cloneable {
    private int stateBefore;
    private int u;
    private int v;
    private int stateAfter;
    private double distanceToRt;
    private Point rt;
    public Edge(int state, int u, Point rt) {
        stateBefore = state;
        this.u = u;
        this.rt = rt;
        Point temp = Coder.encode(state, u);
        v = (int)temp.getX();
        stateAfter = (int)temp.getY();

        Modulator modulator = new Modulator();
        Point s = modulator.modulate(v);
        distanceToRt = getDistance(rt, s);
    }

    public Edge(String edge) {
        String[] fields = edge.split(" ");
        if(fields.length == 5) {
            stateBefore = Integer.parseInt(fields[0]);
            u = Integer.parseInt(fields[1]);
            rt = new Point(Double.parseDouble(fields[2]), Double.parseDouble(fields[3]), Integer.parseInt(fields[4]));
            Point temp = Coder.encode(stateBefore, u);
            v = (int)temp.getX();
            stateAfter = (int)temp.getY();

            Modulator modulator = new Modulator();
            Point s = modulator.modulate(v);
            distanceToRt = getDistance(rt, s);
        }
    }

    public double getDistance(Point first, Point second) {
        double result = Math.pow(first.getX() - second.getX(), 2) + Math.pow(first.getY() - second.getY(), 2);
        return result;
    }

    public Point getRt() {
        return rt;
    }

    public int getStateBefore() {
        return stateBefore;
    }

    public int getStateAfter() {
        return stateAfter;
    }

    public int getU() {
        return u;
    }

    public int getV() {
        return v;
    }

    public double getDistanceToRt() {
        return distanceToRt;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "stateBefore=" + stateBefore +
                ", u=" + u +
                ", v=" + v +
                ", stateAfter=" + stateAfter +
                ", distanceToRt=" + distanceToRt +
                '}';
    }

    public String textForConstructor() {
        return stateBefore + " " + u + " " + rt.getX() + " " + rt.getY() + " " + rt.getData();
    }
}
