package com.fictional_particle_sim.physicals;

import com.fictional_particle_sim.geometrics.BoundingBox;
import com.fictional_particle_sim.geometrics.Point;
import com.fictional_particle_sim.geometrics.Vector;

import java.awt.*;

public class Barrier {

    public Vector line;
    public Point center, topLeft, bottomRight;
    public double radius;
    public String shape;
    public Color color;

    public Barrier(Vector v, Color color) {
        line = v;
        this.color = color;
        shape = "LINE";

        center = new Point();
        topLeft = new Point();
        bottomRight = new Point();
    }
    public Barrier(Point center, double radius, Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
        shape = "CIRCLE";

        line = new Vector();
        topLeft = new Point();
        bottomRight = new Point();

    }
    public Barrier(Point topLeft, Point bottomRight, Color color) {

        this.topLeft = new Point(Math.min(topLeft.x, bottomRight.x), Math.min(topLeft.y, bottomRight.y));
        this.bottomRight = new Point(Math.max(topLeft.x, bottomRight.x), Math.max(topLeft.y, bottomRight.y));
        this.color = color;
        this.shape = "RECTANGLE";

        line = new Vector();
        center = new Point();
    }

    public BoundingBox boundingBox() {

        return new BoundingBox(this);
    }
}
