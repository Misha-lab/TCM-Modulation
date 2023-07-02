package com.company;

import javax.swing.*;
import java.awt.*;

public class Client extends JFrame {

    private int fullScreenWidth;
    private int fullScreenHeight;


    public Client() {
        super("TCM");

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        fullScreenHeight = dimension.height - 30;
        fullScreenWidth = dimension.width;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setIconImage(new ImageIcon("pictures/icon.jpg").getImage());
        Container c = getContentPane();
        //setLayout(null);
        setBounds(0,0,fullScreenWidth, fullScreenHeight);
        setResizable(false);

        setVisible(true);
    }

    public void add(JScrollPane scrollPane) {
        getContentPane().removeAll();
        getContentPane().add(scrollPane);
        repaint();
        revalidate();
    }

    public void add(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel);
        repaint();
        revalidate();
    }

    public int getFullScreenHeight() {
        return fullScreenHeight;
    }

    public int getFullScreenWidth() {
        return fullScreenWidth;
    }
}
