package com.company;

import java.util.Random;

public class Channel {

    private double E = 1;
    public Point addNoise(Point point, double SNR) {
        Random random = new Random();
        //random.setSeed(System.currentTimeMillis());
        double Y = Math.pow(10, SNR / 10);

        double N0 = E/Y;
        Point result = new Point();
        result.setX(point.getX() + random.nextGaussian() * Math.sqrt(N0/2));
        result.setY(point.getY() + random.nextGaussian() * Math.sqrt(N0/2));
        return result;
    }

    public void printN0(double SNR) {
        double Y = Math.pow(10, SNR / 10);
        double N0 = E/Y;
        System.out.println("SNR: " + SNR + " ||| sqrt(N0/2): " + Math.sqrt(N0/2));
    }
}
