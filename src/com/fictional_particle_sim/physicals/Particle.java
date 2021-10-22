package com.fictional_particle_sim.physicals;

import com.fictional_particle_sim.geometrics.*;
import com.fictional_particle_sim.geometrics.Point;

import java.awt.*;
import java.util.Objects;

import static com.fictional_particle_sim.Constants.*;

public class Particle {

    public Point pos, lastPos;
    public Vector vel;
    public double mass, charge, maxCharge;
    public boolean fixedCharge, fixedVel;

    public Color color;

    public Particle(Point pos) {
        this.lastPos = pos;
        this.pos = pos;
        this.vel = new Vector();

        this.mass = 1;
        this.charge = 0;
        this.maxCharge = 0;

        this.fixedCharge = false;
        this.fixedVel = false;
    }
    public Particle(Point pos, Vector vel) {
        this.lastPos = pos;
        this.pos = pos;
        this.vel = vel.center(pos);

        this.mass = 1;
        this.charge = 0;
        this.maxCharge = 0;

        this.fixedCharge = false;
        this.fixedVel = false;
    }
    public Particle(Point pos, double mass, double charge) {
        this.lastPos = pos;
        this.pos = pos;
        this.vel = new Vector();

        this.mass = mass;
        this.charge = charge;
        this.maxCharge = 0;

        this.fixedCharge = false;
        this.fixedVel = false;
    }
    public Particle(Point pos, Vector vel, double mass, double charge) {
        this.lastPos = pos;
        this.pos = pos;
        this.vel = vel.center(pos);

        this.mass = mass;
        this.charge = charge;
        this.maxCharge = 0;

        this.fixedCharge = false;
        this.fixedVel = false;
    }
    public Particle(Point pos, boolean fixedCharge, boolean fixedVel) {
        this.lastPos = pos;
        this.pos = pos;
        this.vel = new Vector();

        this.mass = 1;
        this.charge = 0;
        this.maxCharge = 0;

        this.fixedCharge = fixedCharge;
        this.fixedVel = fixedVel;
    }
    public Particle(Point pos, Vector vel, boolean fixedCharge, boolean fixedVel) {
        this.lastPos = pos;
        this.pos = pos;
        this.vel = vel.center(pos);

        this.mass = 1;
        this.charge = 0;
        this.maxCharge = 0;

        this.fixedCharge = fixedCharge;
        this.fixedVel = fixedVel;
    }
    public Particle(Point pos, double mass, double charge, boolean fixedCharge, boolean fixedVel) {
        this.lastPos = pos;
        this.pos = pos;
        this.vel = new Vector();

        this.mass = mass;
        this.charge = charge;
        this.maxCharge = 0;

        this.fixedCharge = fixedCharge;
        this.fixedVel = fixedVel;
    }
    public Particle(Point pos, Vector vel, double mass, double charge, boolean fixedCharge, boolean fixedVel) {
        this.lastPos = pos;
        this.pos = pos;
        this.vel = vel.center(pos);

        this.mass = mass;
        this.charge = charge;
        this.maxCharge = 0;

        this.fixedCharge = fixedCharge;
        this.fixedVel = fixedVel;
    }

    //Generates a Particle with Random Position
    public static Particle random(double mass) {
        return new Particle(new Point(Math.random() * SCALE_WIDTH, Math.random() * SCALE_HEIGHT), mass, (Math.random() * 2) - 1);
    }
    public static Particle random(Vector vel, double mass) {
        return new Particle(new Point(Math.random() * SCALE_WIDTH, Math.random() * SCALE_HEIGHT), vel, mass, (Math.random() * 2) - 1);
    }
    public static Particle random(double mass, double charge) {
        return new Particle(new Point(Math.random() * SCALE_WIDTH, Math.random() * SCALE_HEIGHT), mass, charge);
    }
    public static Particle random(Vector vel, double mass, double charge) {
        return new Particle(new Point(Math.random() * SCALE_WIDTH, Math.random() * SCALE_HEIGHT), vel, mass, charge);
    }
    public static Particle random(double mass, double charge, boolean fixedCharge, boolean fixedVel) {
        return new Particle(new Point(Math.random() * SCALE_WIDTH, Math.random() * SCALE_HEIGHT), mass, charge, fixedCharge, fixedVel);
    }
    public static Particle random(Vector vel, double mass, double charge, boolean fixedCharge, boolean fixedVel) {
        return new Particle(new Point(Math.random() * SCALE_WIDTH, Math.random() * SCALE_HEIGHT), vel, mass, charge, fixedCharge, fixedVel);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }
    public void setVel(Vector vel) {
        this.vel = vel;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }
    public void setCharge(double charge) {
        this.charge = charge;
    }
    public void setMaxCharge(double maxCharge) {
        this.maxCharge = maxCharge;
    }

    public void setFixedCharge(boolean fixedCharge) {
        this.fixedCharge = fixedCharge;
    }
    public void setFixedVel(boolean fixedVel) {
        this.fixedVel = fixedVel;
    }

    // Calculations for Individual Particles
    public Vector force(Particle b) {

        double dist = this.pos.dist(b.pos);
        if (dist != 0) {

            Vector direct = new Vector(this.pos, b.pos).norm();
            double distCube = TWO_PI_SQUARED * Math.pow(dist, 3);
            if (dist > 2 * MIN_DIST) {
                double forceM = this.charge * b.charge / distCube;
                return direct.scalar(forceM);
            } else if (dist > MIN_DIST) {
                double forceM = (this.charge * b.charge/2 - PUSHBACK_FORCE * this.vel.proj(direct).sub(b.vel.proj(direct)).magnitude()) / distCube;
                return direct.scalar(forceM);
            } else if (dist == MIN_DIST) {
                return new Vector();
            } else {
                double forceM = PUSHBACK_FORCE * this.vel.proj(direct).sub(b.vel.proj(direct)).magnitude() / distCube;
                return direct.scalar(forceM);
            }

        } else return new Vector();
    }
    public Vector acc(Vector force) {

        return force.scalar( 1 / this.mass );
    }
    public void applyAcc(Vector acc) {

        vel = vel.add(acc);
        if (vel.magnitude() > MAX_VEL) {
            vel = vel.norm().scalar(MAX_VEL);
        }
    }
    public void applyVel() {

        lastPos = pos;
        pos = pos.add(vel.center().scalar(SPF).end);
    }
    public double maxCharge(Particle b) {

        if (!this.equals(b)) {
            double dist = this.pos.dist(b.pos);

            if (dist != 0) {
                return CHARGE_FORCE * b.charge / (TWO_PI_SQUARED * Math.pow(dist, 3));
            } else return b.charge / Math.abs(b.charge);
        } else return 0;
    }
    public double charge(Particle b) {

        if (!this.equals(b)) {
            double dist = this.pos.dist(b.pos);
            if (dist != 0) {
                return DELTA_CHARGE_FORCE * b.charge / (2 * TWO_PI_SQUARED * Math.pow(dist, 2));
            } else return b.charge / Math.abs(b.charge);
        } else return 0;

    }
    public void collide(Barrier b) {
        if (b.line.between(this.pos, this.lastPos)) {
            pos = pos.sub(pos.disp(b.line).center().end.mult(1.001));
            vel = vel.sub(vel.perp(b.line).scalar(2));
        }
    }
    public void chargeColor() {
        if (charge == 0) {
            color = new Color(128, 128, 128);
        } else if (charge >= 1) {
            color = new Color(36, 162, 26);
        } else if (charge <= -1) {
            color = new Color(229, 43, 43);
        } else if (charge > 0) {
            color = new Color((int)(36*(charge) + 128*(1-charge)), (int)(162*(charge) + 128*(1-charge)), (int)(26*(charge) + 128*(1-charge)));
        } else {
            color = new Color((int)(229*(-charge) + 128*(1+charge)), (int)(43*(-charge) + 128*(1+charge)), (int)(43*(-charge) + 128*(1+charge)));
        }
        if(mass < 0) {
            color = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Particle particle = (Particle) o;
        return Double.compare(particle.mass, mass) == 0 && Double.compare(particle.charge, charge) == 0 && Double.compare(particle.maxCharge, maxCharge) == 0 && fixedCharge == particle.fixedCharge && fixedVel == particle.fixedVel && Objects.equals(pos, particle.pos) && Objects.equals(vel, particle.vel) && Objects.equals(color, particle.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos, vel, mass, charge, maxCharge, fixedCharge, fixedVel, color);
    }

    @Override
    public String toString() {
        return "Particle{" +
                "pos = " + pos +
                ", lastPos = " + lastPos +
                ", vel = " + vel.center().end +
                ", mass = " + mass +
                ", charge = " + charge +
                ", max charge = " + maxCharge +
                '}';
    }
}
