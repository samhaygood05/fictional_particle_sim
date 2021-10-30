package com.fictional_particle_sim;

import com.fictional_particle_sim.geometrics.Point;
import com.fictional_particle_sim.geometrics.Vector;
import com.fictional_particle_sim.physicals.Barrier;
import com.fictional_particle_sim.physicals.ParticleSystem;

import java.awt.*;

public class Constants {

    // Math Constants
    public static final double TWO_PI_SQUARED = 2 * Math.pow(Math.PI, 2);

    // Screen Constants
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 1200 / 16 * 9;
    public static final Point CENTER = new Point(WIDTH/2, HEIGHT/2);

    // Simulation Constants
    public static final int FPS = 1000;
    public static final double SPF = 0.0001;
    public static final double MAX_DISTANCE = 20000;
    public static final int PPU = 200;
    public static final double SCALE_WIDTH = (double)WIDTH/PPU;
    public static final double SCALE_HEIGHT = (double)HEIGHT/PPU;
    public static final Point SCALED_CENTER = new Point(SCALE_WIDTH/2, SCALE_HEIGHT/2);
    public static final String EDGE_BEHAVIOR = "BORDER"; // Can be BORDER or LOOP

    // Interaction Constants
    public static final double CHARGE_FORCE = .5;
    public static final double DELTA_CHARGE_FORCE = 1 * SPF;
    public static final double MIN_DIST = 0.3;
    public static final double PUSHBACK_FORCE = -0.01;
    public static final double MAX_VEL = 100;
    public static final double MAX_CHARGE = 1;

    // Debug Constants
    public static final boolean DEBUG = false;

    public static ParticleSystem particleSystem = new ParticleSystem();

}
