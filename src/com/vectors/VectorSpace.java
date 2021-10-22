package com.vectors;

import javax.swing.*;
import java.awt.*;

public class VectorSpace extends JFrame {

    private TheCanvas canvas = new TheCanvas();


    public VectorSpace() {

        setLayout(new BorderLayout());
        setSize(1200, 1200/16 * 9);
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
