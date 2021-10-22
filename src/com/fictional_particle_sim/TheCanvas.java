package com.fictional_particle_sim;

import com.vectors.geometrics.Point;
import com.vectors.geometrics.Vector;

import java.awt.*;
import static com.vectors.Constants.*;

public class TheCanvas extends Canvas{


    @Override
    public void paint(Graphics g) {

        Main.computeAndDraw(g, this);

    }

    public static void drawVector(Graphics g, Vector v, boolean arrow, double l, double h) {
        g.setColor(v.color);
        g.drawLine((int)Math.round(v.start.x), (int)Math.round(v.start.y), (int)Math.round(v.end.x),(int)Math.round(v.end.y));
        if (arrow) {
            g.drawLine((int)Math.round(v.end.x - h * (v.end.y - v.start.y) - (l * (v.end.x - v.start.x))), (int)Math.round(v.end.y + h * (v.end.x - v.start.x) - (l * (v.end.y - v.start.y))), (int)Math.round(v.end.x),(int)Math.round(v.end.y));
            g.drawLine((int)Math.round(v.end.x + h * (v.end.y - v.start.y) - (l * (v.end.x - v.start.x))), (int)Math.round(v.end.y - h * (v.end.x - v.start.x) - (l * (v.end.y - v.start.y))), (int)Math.round(v.end.x),(int)Math.round(v.end.y));
        }
    }
    public static void drawVector(Graphics g, Vector v, boolean arrow, double l, double h, Color color) {
        g.setColor(color);
        g.drawLine((int)Math.round(v.start.x), (int)Math.round(v.start.y), (int)Math.round(v.end.x),(int)Math.round(v.end.y));
        if (arrow) {
            g.drawLine((int)Math.round(v.end.x - h * (v.end.y - v.start.y) - (l * (v.end.x - v.start.x))), (int)Math.round(v.end.y + h * (v.end.x - v.start.x) - (l * (v.end.y - v.start.y))), (int)Math.round(v.end.x),(int)Math.round(v.end.y));
            g.drawLine((int)Math.round(v.end.x + h * (v.end.y - v.start.y) - (l * (v.end.x - v.start.x))), (int)Math.round(v.end.y - h * (v.end.x - v.start.x) - (l * (v.end.y - v.start.y))), (int)Math.round(v.end.x),(int)Math.round(v.end.y));
        }
    }
    public static void drawVectors(Graphics g, Vector[] v, boolean arrow, double l, double h) {
        for (Vector vector : v) {
            drawVector(g, vector, arrow, l, h);
        }

    }

    public static void drawPoint(Graphics g, com.vectors.geometrics.Point p, int r, Color color) {
        g.setColor(color);
        g.fillOval((int)Math.round(p.x)-r, (int)Math.round(p.y)-r, 2*r, 2*r);
    }
    public static void drawPoints(Graphics g, com.vectors.geometrics.Point[] p, int r, Color color) {
        for (Point point : p) {
            drawPoint(g, point, r, color);
        }
    }

}
