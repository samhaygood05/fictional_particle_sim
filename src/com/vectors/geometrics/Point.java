package com.vectors.geometrics;

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

    public boolean equals(Point b) {

        return this.x == b.x && this.y == b.y;
    }

    @Override
    public String toString() {
        return "( " + x + " , " + y + " )";
    }
}
