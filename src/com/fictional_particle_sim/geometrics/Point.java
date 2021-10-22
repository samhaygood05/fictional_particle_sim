package com.fictional_particle_sim.geometrics;

public class Point {
    public double x, y;
    public Point() {
        x = 0;
        y = 0;
    }
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point add(Point b) {

        return new Point(this.x + b.x, this.y + b.y);
    }

    public Point sub(Point b) {

        return new Point(this.x - b.x, this.y - b.y);
    }

    public Point mult(double b) {

        return new Point(this.x * b, this.y * b);
    }

    public double dist(Point b) {

        return Math.hypot(b.x-this.x, b.y-this.y);
    }
    public Vector disp(Vector b) {
        Vector temp = new Vector(b.start, this);
        return temp.perp(b);
    }

    public boolean between(Vector a, Vector b) {
        Vector[] dispA = disp(a).comp();
        Vector[] dispB = disp(b).comp();

        return dispA[0].opposite(dispB[0]) && dispA[1].opposite(dispB[1]);
    }

    public boolean equals(Point b) {

        return this.x == b.x && this.y == b.y;
    }

    @Override
    public String toString() {
        return "( " + x + " , " + y + " )";
    }
}
