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

    public BoundingBox boundingBox() {

        return new BoundingBox(this);
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

    public Vector rotate(double angle) {

        return new Vector(this.magnitude(), this.angle() + angle).shift(this.start);
    }

    public boolean between(Point a, Point b) {
        Vector ab = new Vector(a, b);
        if (ab.boundingBox().intersect(boundingBox())) {
            return linesIntersect(this, ab);
        } else return false;
    }

    private static boolean linesIntersect(Vector v1, Vector v2) {
        double x1 = v1.start.x;
        double y1 = v1.start.y;
        double x2 = v1.end.x;
        double y2 = v1.end.y;

        double x3 = v2.start.x;
        double y3 = v2.start.y;
        double x4 = v2.end.x;
        double y4 = v2.end.y;

        if (x1 == x2 && y1 == y2 || x3 == x4 && y3 == y4) {
            return false;
        }

        double ax = x2 - x1;
        double ay = y2 - y1;
        double bx = x3 - x4;
        double by = y3 - y4;
        double cx = x1 - x3;
        double cy = y1 - y3;

        double alphaNumerator = by * cx - bx * cy;
        double commonDenominator = ay * bx - ax * by;
        if (commonDenominator > 0) {
            if (alphaNumerator < 0 || alphaNumerator > commonDenominator) {
                return false;
            }
        } else if (commonDenominator < 0) {
            if (alphaNumerator > 0 || alphaNumerator < commonDenominator) {
                return false;
            }
        }
        double betaNumerator = ax * cy - ay * cx;
        if (commonDenominator > 0) {
            if (betaNumerator < 0 || betaNumerator > commonDenominator) {
                return false;
            }
        } else if (commonDenominator < 0) {
            if (betaNumerator > 0 || betaNumerator < commonDenominator) {
                return false;
            }
        }
        if (commonDenominator == 0) {

            double y3LessY1 = y3 - y1;
            double collinearityTestForP3 = x1 * (y2 - y3) + x2 * (y3LessY1) + x3 * (y1 - y2);

            if (collinearityTestForP3 == 0) {
                if (x1 >= x3 && x1 <= x4 || x1 <= x3 && x1 >= x4 || x2 >= x3 && x2 <= x4 || x2 <= x3 && x2 >= x4 || x3 >= x1 && x3 <= x2 || x3 <= x1 && x3 >= x2) {
                    return y1 >= y3 && y1 <= y4 || y1 <= y3 && y1 >= y4 || y2 >= y3 && y2 <= y4 || y2 <= y3 && y2 >= y4 || y3 >= y1 && y3 <= y2 || y3 <= y1 && y3 >= y2;
                }
            }
            return false;
        }
        return true;
    }

    public boolean opposite(Vector b) {

        return this.norm().equals(b.norm().scalar(-1));
    }

    @Override
    public String toString() {
        return center().end.toString();
    }
}
