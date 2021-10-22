package com.fictional_particle_sim.geometrics;

import java.awt.*;

public class Vector {
    public Point start, end;
    public Color color;

    public Vector() {
        start = new Point();
        end= new Point();
    }
    public Vector(Point end) {
        this.start = new Point();
        this.end = end;
    }
    public Vector(Point end, Color color) {
        this.start = new Point();
        this.end = end;
        this.color = color;
    }
    public Vector(Point start, Point end) {
        this.start = start;
        this.end = end;
    }
    public Vector(Point start, Point end, Color color) {
        this.start = start;
        this.end = end;
        this.color = color;
    }
    public Vector(double r, double theta) {
        this.start = new Point();

        this.end = new Point(r * Math.cos(theta), r * Math.sin(theta));
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vector center() {

        return new Vector(new Point(), this.end.sub(this.start), this.color);
    }
    public Vector center(Point c) {
        return this.center().shift(c);
    }

    public double angle() {
        Vector v1 = this.center();

        return Math.atan2(v1.end.y, v1.end.x);
    }
    public double magnitude() {

        return Math.hypot(this.center().end.x, this.center().end.y);
    }
    public Vector norm() {
        return this.scalar(1/this.magnitude());
    }

    public Vector scalar(double b) {

        return new Vector(this.center().end.mult(b), this.color).shift(this.start);
    }
    public Vector scaleFromOrigin(double b) {

        return new Vector(this.start.mult(b), this.end.mult(b), this.color);
    }

    public Vector shift(Point b) {
        return new Vector(this.start.add(b), this.end.add(b), this.color);
    }

    public Vector add(Vector b) {
        Vector v1 = this.center();
        Vector v2 = b.center();

        Vector temp = new Vector(v1.end.add(v2.end));
        return temp.shift(this.start);
    }
    public Vector sub(Vector b) {

        return this.add(b.scalar(-1));
    }

    public Vector[] comp() {
        Vector x = new Vector(new Point(this.start.x, this.start.y), new Point(this.end.x, this.start.y));
        Vector y = new Vector(new Point(this.start.x, this.start.y), new Point(this.start.x, this.end.y));
        x.setColor(Color.RED);
        y.setColor(Color.GREEN);

        return new Vector[]{x, y};
    }
    public Vector[] comp(boolean xToY) {
        Vector x = new Vector(new Point(this.start.x, this.start.y), new Point(this.end.x, this.start.y));
        Vector y = new Vector(new Point(this.start.x, this.start.y), new Point(this.start.x, this.end.y));
        x.setColor(Color.RED);
        y.setColor(Color.GREEN);
        if (xToY) {
            y = y.shift(new Point(this.center().end.x, 0));
        } else {
            x = x.shift(new Point(0, this.center().end.y));
        }

        return new Vector[]{x, y};
    }

    public boolean equals(Vector b) {
        Vector v1 = this.center();
        Vector v2 = b.center();

        return v1.end.equals(v2.end);
    }

    public double dot(Vector b) {
        Vector v1 = this.center();
        Vector v2 = b.center();

        return v1.end.x * v2.end.x + v1.end.y * v2.end.y;
    }
    public Vector proj(Vector b) {

        return b.norm().scalar(this.dot(b.norm())).center().shift(this.start);
    }
    public Vector perp(Vector b) {

        return this.sub(this.proj(b));
    }
    public Vector[] projComp(Vector b) {
        Vector x = this.proj(b);
        Vector y = this.perp(b);

        x.setColor(Color.RED);
        y.setColor(Color.GREEN);

        return  new Vector[]{x, y};
    }
    public Vector[] projComp(Vector b, boolean xToY) {
        Vector x = this.proj(b);
        Vector y = this.perp(b);
        x.setColor(Color.RED);
        y.setColor(Color.GREEN);

        if (xToY) {
            y = y.shift(x.center().end);
        } else {
            x = x.shift(y.center().end);
        }

        return  new Vector[]{x, y};
    }

    public boolean between(Point a, Point b) {

        return a.disp(this).opposite(b.disp(this));
    }

    public boolean opposite(Vector b) {

        return this.norm().equals(b.norm().scalar(-1));
    }

    @Override
    public String toString() {
        return center().end.toString();
    }
}
