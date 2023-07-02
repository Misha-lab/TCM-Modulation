package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

class Line extends JPanel {
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    public Line(int x1, int x2, int y1, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(x1,y1,x2,y2);
    }
}

public class PathsView extends JPanel {
    private Client client;
    private ArrayList<ArrayList<Edge>> paths;
    public PathsView(Client client) {
        this.client = client;
        setLayout(null);
    }

    public void update(ArrayList<ArrayList<Edge>> paths) {
        removeAll();
        this.paths = paths;
        ArrayList<ArrayList<JPanel>> dots;
        int width = client.getFullScreenWidth();
        dots = new ArrayList<>();
        for (int i = 0; i < paths.size(); i++) {
            dots.add(new ArrayList<>());
            for (int j = 0; j < paths.get(i).size() + 1; j++) {
                JPanel dot = new JPanel();
                width = 150 + j * (10 + 50);
                dot.setBounds(100 + j * (10 + 50),100 + i * (10 + 100), 10, 10);
                dot.setBackground(Color.red);
                dots.get(i).add(dot);

                add(dot);


            }
        }
        for (int i = 0; i < paths.size(); i++) {
            for (int j = 0; j < paths.get(i).size(); j++) {
                Edge edge = paths.get(i).get(j);
                //Line line = new Line(random.nextInt(getWidth()), random.nextInt(getWidth()),
                //        random.nextInt(getHeight()), random.nextInt(getHeight()));
                if(edge != null) {
                    Line line = new Line(dots.get(edge.getStateBefore()).get(j).getX(),
                            dots.get(edge.getStateAfter()).get(j + 1).getX(),
                            dots.get(edge.getStateBefore()).get(j).getY(), dots.get(edge.getStateAfter()).get(j + 1).getY());
                    line.setOpaque(false);
                    line.setBounds(0, 0, client.getFullScreenWidth(), client.getFullScreenHeight());
                    add(line);
                }
            }
        }
        setBounds(new Rectangle(width, client.getFullScreenHeight()));
        repaint();
        revalidate();
    }
}
