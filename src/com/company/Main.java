package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main extends JFrame {

    private int fullScreenWidth;
    private int fullScreenHeight;

    public static void main(String[] args) {

        Client client = new Client();

        PathsView pathsView = new PathsView(client);
        //client.add(pathsView);
        JScrollPane pane = new JScrollPane(pathsView, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setPreferredSize(new Dimension(client.getFullScreenWidth(), client.getFullScreenHeight()));
        pane.getHorizontalScrollBar().setUnitIncrement(20);
        pane.setBorder(null);
        client.add(pane);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            FileWriter fw = new FileWriter("Pe.txt");
            for (double SNR = 1; SNR < 10; SNR++) {
                Coder coder = new Coder();
                Modulator modulator = new Modulator();
                Channel channel = new Channel();
                int L = 20;
                Decoder decoder = new Decoder(L);
                int Nerrmax = 20;
                int Nerr = 0;
                int Ntest = 0;
                ArrayList<Integer> blocks = new ArrayList<>();
                while (Nerr < Nerrmax) {
                    //System.out.println("----------- " + Ntest + " -----------");
                    int u = (int) (Math.random() * 4);
                    int encoded = coder.encode(u);
                    //System.out.println("Encoded symbol: " + encoded);
                    //System.out.println("U: " + u);
                    Point point = modulator.modulate(encoded);
                    point = channel.addNoise(point, SNR);
                    //System.out.println(point);
                    int decoded = decoder.decode(point);
                    //System.out.println("Decoded symbol: " + decoded);
                    if (decoded != -1) {
                        int curU = blocks.remove(0);
                        if (curU != decoded) {
                            Nerr++;
                            System.out.println(Nerr);
                        }
                        Ntest++;
                    }
                    blocks.add(u);

                    ////
                    pathsView.update(decoder.getPaths());

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(SNR + " --- " + Nerr + " : : : " + Ntest);
                fw.write(SNR + " " + (double)Nerr/Ntest + "\r\n");
            }
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            int[] LL = {2, 3, 5, 10, 20, 30, 40, 50};
            double maxSNR = 20;
            int L = 45;
            FileWriter fw = new FileWriter("Pe(SNR)_" + L + ".txt");
            boolean isHighPe = false;
            for (double SNR = 1; SNR < maxSNR && !isHighPe; SNR++) {
                Coder coder = new Coder();
                Modulator modulator = new Modulator();
                Channel channel = new Channel();
                System.out.println("|||||||| L = " + L + " |||||||");
                Decoder decoder = new Decoder(L);
                int Nerrmax = 10;
                if (SNR < 7) {
                    Nerrmax = 200;
                }
                int Nerr = 0;
                int Ntest = 0;

                ArrayList<Integer> blocks = new ArrayList<>();
                boolean flag = true;
                while (Nerr < Nerrmax && flag) {
                    //System.out.println("----------- " + Ntest + " -----------");
                    int u = (int) (Math.random() * 4);
                    int encoded = coder.encode(u);
                    //System.out.println("Encoded symbol: " + encoded);
                    //System.out.println("U: " + u);
                    Point tmp = modulator.modulate(encoded);
                    Point point = modulator.modulate(encoded);
                    point = channel.addNoise(point, SNR);

                    int decoded = decoder.decode(point);
                    //System.out.println("Decoded symbol: " + decoded);
                    if (decoded != -1) {
                        int curU = blocks.remove(0);
                        if (curU != decoded) {
                            Nerr++;
                            System.out.println("SNR: " + SNR + " L: " + L + " Кол-во принятых сигналов: " + Ntest + " Текущее кол-во ошибок: " + Nerr);
                        }
                        Ntest++;
                    }
                    blocks.add(u);

                    if (Ntest > 2000000 || ((Ntest > 200000) && (double) Nerr / Ntest < Math.pow(10, -5))) {
                        if (Nerr != 0) {
                            fw.write(SNR + " " + (double) Nerr / Ntest + "\r\n");
                        }
                        System.out.println(SNR + " " + (double) Nerr / Ntest);
                        flag = false;
                        isHighPe = true;
                    }
                    ////
                    /*pathsView.update(decoder.getPaths());

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }
                //System.out.println("Average noise: " + sumX/Ntest + ", " + sumY/Ntest);
                fw.write(SNR + " " + (double) Nerr / Ntest + "\r\n");
                System.out.println("Отношение сигнал/шум: " + SNR + ", L = " + L + " --- Кол-во ошибок - " + Nerr + " из " + Ntest + " принятых сигналов; Pe = " + (double) Nerr / Ntest);
            }
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
