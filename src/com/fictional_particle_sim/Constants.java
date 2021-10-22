package com.fictional_particle_sim;

import com.fictional_particle_sim.geometrics.Point;
import com.fictional_particle_sim.physicals.Barrier;
import com.fictional_particle_sim.physicals.ParticleSystem;

public class Constants {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 1200 / 16 * 9;
    public static final Point CENTER = new Point(WIDTH/2, HEIGHT/2);

    public static final int FPS = 1000;
    public static final double SPF = 0.0001;
    public static final double MAX_DISTANCE = 20000;
    public static final int PPU = 100;
    public static final double SCALE_WIDTH = (double)WIDTH/PPU;
    public static final double SCALE_HEIGHT = (double)HEIGHT/PPU;

    public static final double CHARGE_FORCE = .5;
    public static final double DELTA_CHARGE_FORCE = 1;
    public static final double MIN_DIST = 0.3;
    public static final double PUSHBACK_FORCE = -0.01;
    public static final double MAX_VEL = 50;

    public static ParticleSystem particleSystem = new ParticleSystem();

    public static final Barrier[] BORDER = new Barrier[] {
            new Barrier(new Point(-10, -10), new Point(0, SCALE_HEIGHT + 10)),
            new Barrier(new Point(-10,-10), new Point(SCALE_WIDTH + 10, 0)),
            new Barrier(new Point(SCALE_WIDTH, -10), new Point(SCALE_WIDTH + 10, SCALE_HEIGHT +10)),
            new Barrier(new Point(-10, SCALE_HEIGHT), new Point(SCALE_WIDTH +10, SCALE_HEIGHT + 10))
    };

}
