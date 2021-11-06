package com.fictional_particle_sim.physicals;

import com.fictional_particle_sim.geometrics.BoundingBox;
import com.fictional_particle_sim.geometrics.Point;
import com.fictional_particle_sim.geometrics.Vector;

import java.awt.*;

import static com.fictional_particle_sim.Constants.*;

public class Field {

    public Point center, topLeft, bottomRight;
    public Vector force;
    public double radius, velocityScalar, chargeScalar;
    public String shape;
    public Color color;

    public BoundingBox boundingBox;

    public Field(Point topLeft, Point bottomRight, Color color, double velocityScalar, double chargeScalar) {
        this.topLeft = new Point(Math.min(topLeft.x, bottomRight.x), Math.min(topLeft.y, bottomRight.y));
        this.bottomRight = new Point(Math.max(topLeft.x, bottomRight.x), Math.max(topLeft.y, bottomRight.y));
        this.center = new Point();
        this.color = color;

        this.velocityScalar = ((velocityScalar - 1) * SPF) + 1;
        this.chargeScalar = ((chargeScalar - 1) * SPF) + 1;
        this.force = new Vector();

        shape = "RECTANGLE";
        this.boundingBox = boundingBox();
    }
    public Field(Point topLeft, Point bottomRight, Color color, Vector force) {
        this.topLeft = new Point(Math.min(topLeft.x, bottomRight.x), Math.min(topLeft.y, bottomRight.y));
        this.bottomRight = new Point(Math.max(topLeft.x, bottomRight.x), Math.max(topLeft.y, bottomRight.y));
        this.center = new Point();
        this.color = color;

        this.velocityScalar = 1;
        this.chargeScalar = 1;
        this.force = force;

        shape = "RECTANGLE";
        this.boundingBox = boundingBox();
    }

    public Field(Point center, double radius, Color color, double velocityScalar, double chargeScalar) {
        this.center = center;
        this.radius = radius;
        this.topLeft = new Point();
        this.bottomRight = new Point();
        this.color = color;

        this.velocityScalar = ((velocityScalar - 1) * SPF) + 1;
        this.chargeScalar = ((chargeScalar - 1) * SPF) + 1;
        this.force = new Vector();

        shape = "CIRCLE";
        this.boundingBox = boundingBox();
    }
    public Field(Point center, double radius, Color color, Vector force) {
        this.center = center;
        this.radius = radius;
        this.topLeft = new Point();
        this.bottomRight = new Point();
        this.color = color;

        this.velocityScalar = 1;
        this.chargeScalar = 1;
        this.force = force;

        shape = "CIRCLE";
        this.boundingBox = boundingBox();
    }

    public BoundingBox boundingBox() {

        return new BoundingBox(this);
    }
}
