package com.fictional_particle_sim.physicals;

import com.fictional_particle_sim.geometrics.Point;
import com.fictional_particle_sim.geometrics.Vector;

import java.awt.*;

public class Barrier {

    public Vector line, perpLine1, perpLine2;


    public Barrier(Vector v) {
        line = v;

        perpLine1 = v.rotate(Math.PI/2);
        perpLine2 = perpLine1.center(line.end);
    }
}
