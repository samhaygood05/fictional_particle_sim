package com.fictional_particle_sim;

import com.fictional_particle_sim.geometrics.Point;
import com.fictional_particle_sim.geometrics.Vector;
import com.fictional_particle_sim.physicals.Barrier;

import java.awt.*;

import static com.fictional_particle_sim.Constants.PPU;

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
    public static void drawBarriers(Graphics g, Barrier[] v) {
        for (Barrier barrier : v) {
            g.setColor(barrier.color);
            switch (barrier.shape) {
                case "LINE": g.drawLine((int) Math.round(barrier.line.start.x * PPU), (int) Math.round(barrier.line.start.y * PPU), (int) Math.round(barrier.line.end.x * PPU), (int) Math.round(barrier.line.end.y * PPU));
                case "CIRCLE": g.drawOval((int)((barrier.center.x - barrier.radius) * PPU), (int)((barrier.center.y - barrier.radius) * PPU), (int)(2 * barrier.radius * PPU),(int)(2 * barrier.radius * PPU));
                case "RECTANGLE": g.drawRect((int)(barrier.topLeft.x * PPU), (int)(barrier.topLeft.y * PPU), (int)((barrier.bottomRight.x - barrier.topLeft.x) * PPU), (int)((barrier.bottomRight.y - barrier.topLeft.y) * PPU));
            }
        }

    }

    public static void drawPoint(Graphics g, com.fictional_particle_sim.geometrics.Point p, int r, Color color) {
        g.setColor(color);
        g.fillOval((int)Math.round(p.x)-r, (int)Math.round(p.y)-r, 2*r, 2*r);
    }
    public static void drawPoints(Graphics g, com.fictional_particle_sim.geometrics.Point[] p, int r, Color color) {
        for (Point point : p) {
            drawPoint(g, point, r, color);
        }
    }

}
