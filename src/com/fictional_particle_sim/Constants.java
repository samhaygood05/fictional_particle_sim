package com.fictional_particle_sim;

import com.vectors.geometrics.Point;
import com.vectors.geometrics.Vector;
import com.vectors.physicals.Particle;
import com.vectors.physicals.ParticleSystem;

import java.awt.*;

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

}
