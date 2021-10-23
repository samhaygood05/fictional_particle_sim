package com.fictional_particle_sim;

import javax.swing.*;
import java.awt.*;

public class VectorSpace extends JFrame {

    private TheCanvas canvas = new TheCanvas();


    public VectorSpace() {

        setLayout(new BorderLayout());
        setSize((int)(Constants.WIDTH), (int)(Constants.HEIGHT));
        setTitle("Vector Space");
        add("Center", canvas);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        setVisible(true);
    }
    public VectorSpace(int width, int height) {

        setLayout(new BorderLayout());
        setSize(width, height);
        setTitle("Vector Space");
        add("Center", canvas);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
