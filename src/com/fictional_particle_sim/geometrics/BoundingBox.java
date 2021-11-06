package com.fictional_particle_sim.geometrics;

import com.fictional_particle_sim.physicals.Barrier;
import com.fictional_particle_sim.physicals.Field;

public class BoundingBox {

    public Point a, b;

    public BoundingBox(Point a, Point b) {
        this.a = new Point(Math.min(a.x, b.x), Math.min(a.y, b.y));
        this.b = new Point(Math.max(a.x, b.x), Math.max(a.y, b.y));
    }
    public BoundingBox(Vector v) {

        a = new Point(Math.min(v.start.x, v.end.x), Math.min(v.start.y, v.end.y));
        b = new Point(Math.max(v.start.x, v.end.x), Math.max(v.start.y, v.end.y));
    }
    public BoundingBox(Barrier v) {

        switch (v.shape){
            case "LINE" -> {
                a = new BoundingBox(v.line).a;
                b = new BoundingBox(v.line).b;
            } case "CIRCLE" -> {
                a = v.center.sub(new Point(v.radius, v.radius));
                b = v.center.add(new Point(v.radius, v.radius));
            } case "RECTANGLE" -> {
                a = v.topLeft;
                b = v.bottomRight;
            }
        }
    }
    public BoundingBox(Field v) {

        switch (v.shape){
            case "CIRCLE" -> {
                a = v.center.sub(new Point(v.radius, v.radius));
                b = v.center.add(new Point(v.radius, v.radius));
            } case "RECTANGLE" -> {
                a = v.topLeft;
                b = v.bottomRight;
            }
        }
    }

    public boolean intersect(BoundingBox b) {

        return !(this.b.x < b.a.x || this.a.x > b.b.x || this.b.y < b.a.y || this.a.y > b.b.y);
    }

    public BoundingBox add(BoundingBox b) {

        return new BoundingBox(new Point(Math.min(a.x, b.a.x), Math.min(a.y, b.a.y)), new Point(Math.max(this.b.x, b.b.x), Math.max(this.b.y, b.b.y)));
    }
}
