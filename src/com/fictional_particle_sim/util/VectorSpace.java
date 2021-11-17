package com.fictional_particle_sim.util;

import javax.swing.*;
import java.awt.*;

public class VectorSpace extends JFrame {

    private final TheCanvas canvas = new TheCanvas();


    public VectorSpace() {

        setLayout(new BorderLayout());
        setSize(Constants.WIDTH, Constants.HEIGHT);
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
