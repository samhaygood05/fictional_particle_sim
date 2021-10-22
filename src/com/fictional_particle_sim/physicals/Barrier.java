package com.fictional_particle_sim.physicals;

import com.fictional_particle_sim.geometrics.Point;
import com.fictional_particle_sim.geometrics.Vector;

import java.awt.*;

public class Barrier {

    public Point corn1, corn2;
    public Vector left, right, up, down;

    public Color color;

    public Barrier(Point corn1, Point corn2) {
        this.corn1 = new Point(Math.min(corn1.x, corn2.x), Math.min(corn1.y, corn2.y));
        this.corn2 = new Point(Math.max(corn1.x, corn2.x), Math.max(corn1.y, corn2.y));

        left = new Vector(this.corn1, new Point(this.corn1.x, this.corn2.y));
        right = new Vector(new Point(this.corn2.x, this.corn1.y), this.corn2);
        up = new Vector(this.corn1, new Point(this.corn2.x, this.corn1.y));
        down = new Vector(new Point(this.corn1.x, this.corn2.y), this.corn2);
    }
}
