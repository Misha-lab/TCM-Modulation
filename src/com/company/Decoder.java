package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Decoder {

    private int L;
    private ArrayList<ArrayList<Edge>> paths;
    private double[] metrics;

    public Decoder(int L) {
        this.L = L;

        metrics = new double[Coder.STATES_COUNT];
        Arrays.fill(metrics, 0.0);

        paths = new ArrayList<>();
        for (int i = 0; i < Coder.STATES_COUNT; i++) {
            paths.add(new ArrayList<>());
        }
    }

    public double getDistance(Point first, Point second) {
        double result = Math.pow(first.getX() - second.getX(), 2) + Math.pow(first.getY() - second.getY(), 2);
        return result;
    }

    public int decode(Point rt) {
        int decoded = -1;
        ArrayList<ArrayList<Edge>> distances = new ArrayList<>();
        for (int i = 0; i < Coder.STATES_COUNT; i++) {
            distances.add(new ArrayList<>());
        }
        for (int i = 0; i < Coder.STATES_COUNT; i++) {
            for (int j = 0; j < Coder.STATES_COUNT; j++) {
                Edge edge = new Edge(i, j, rt);
                distances.get(edge.getStateAfter()).add(edge);
            }
        }
        //System.out.println(distances);

        ArrayList<ArrayList<Edge>> tempPaths = new ArrayList<>();
        double[] tempMetrics = new double[Coder.STATES_COUNT];
        for (int i = 0; i < metrics.length; i++) {
            tempMetrics[i] = metrics[i];
        }

        for (int i = 0; i < Coder.STATES_COUNT; i++) {
            Edge edge = getMinimalMetric(distances.get(i), i, tempMetrics); // метрика обновляется внутри
            ArrayList<Edge> pathsBefore = copyArray(paths.get(edge.getStateBefore()));
            pathsBefore.add(edge);
            tempPaths.add(pathsBefore);
        }
        for (int i = 0; i < Coder.STATES_COUNT; i++) {
            //System.out.println("Метрика до(" + i + "): " + metrics[i]);
            metrics[i] = tempMetrics[i];
            //System.out.println("Метрика после(" + i + "): " + metrics[i]);
            paths.set(i, tempPaths.get(i));
            //System.out.println("Size of paths[" + i + "]: " + paths.get(i).size());
            //System.out.println(paths.get(i));
        }
        Edge edge = null;
        for (int i = 0; i < Coder.STATES_COUNT; i++) {
            if (paths.get(i).size() == L) {
                edge = paths.get(i).remove(0);
            }
        }
        if (edge != null) {
            return edge.getU();
        }

        return decoded;
    }

    public int decode1(Point rt) {
        Modulator modulator = new Modulator();
        double min = getDistance(rt, modulator.getPointOfSignal(0));
        int minIdx = 0;
        for (int i = 1; i < 4; i++) {
            if(getDistance(rt, modulator.getPointOfSignal(i)) < min) {
                min = getDistance(rt, modulator.getPointOfSignal(i));
                minIdx = i;
            }
        }
        return minIdx;
    }

    private ArrayList<Edge> copyArray(ArrayList<Edge> arrayList) {
        ArrayList<Edge> result = new ArrayList<>();
        for (Edge edge : arrayList) {

            Edge temp = null;
            if (edge != null) temp = new Edge(edge.textForConstructor());
            result.add(temp);
        }
        return result;
    }

    private Edge getMinimalMetric(ArrayList<Edge> array, int stateAfter, double[] tempMetrics) {
        double min;
        Edge edge;
        if (array.size() > 0) {
            int stateBefore = array.get(0).getStateBefore();
            min = metrics[stateBefore] + array.get(0).getDistanceToRt();
            edge = array.get(0);
            for (int i = 1; i < array.size(); i++) {
                stateBefore = array.get(i).getStateBefore();
                if (metrics[stateBefore] + array.get(i).getDistanceToRt() < min) {
                    min = metrics[stateBefore] + array.get(i).getDistanceToRt();
                    edge = array.get(i);

                }
            }
            tempMetrics[stateAfter] = min;
            return edge;
        } else {
            throw new RuntimeException("Невозможно найти минимум, размер массива равен нулю");
        }
    }

    public ArrayList<ArrayList<Edge>> getPaths() {
        return paths;
    }
}
