package com.fictional_particle_sim;

import com.fictional_particle_sim.geometrics.Point;
import com.fictional_particle_sim.geometrics.Vector;
import com.fictional_particle_sim.physicals.Particle;

import java.awt.*;

import static com.fictional_particle_sim.Constants.*;


public class Main {

    public static void main(String[] args) {

        particleSystem.particles = new Particle[] {
//                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0,false, false),
//                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
//                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
//                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
//                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
//                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
//                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
//                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
//                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
//                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
//                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
//                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
//                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
//                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 1, true, false),
                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, -1, true, false),
                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
                new Particle((new Point(Math.random()*SCALE_WIDTH, Math.random()*SCALE_HEIGHT)), new Vector(new Point(0, 0)), 1, 0, false, false),
                new Particle((new Point(0.5 + SCALE_WIDTH/2, SCALE_HEIGHT/2)), new Vector(new Point(0, 0)), 1, 0, false, false),
                new Particle((new Point(SCALE_WIDTH/2 - 0.5, SCALE_HEIGHT/2)), new Vector(new Point(0, 0)), 1, 0, false, false),
                new Particle((new Point(SCALE_WIDTH/2 - 0.25, SCALE_HEIGHT/2)), new Vector(new Point(-1, 0)), 1, 0, false, false)
        };
        VectorSpace fr = new VectorSpace(Constants.WIDTH, Constants.HEIGHT);

    }

    public static void computeAndDraw(Graphics g, TheCanvas canvas) {
        while (true){

            //Compute Next Frame
            System.out.println(particleSystem);

            particleSystem.simulate(true);

            g.clearRect(0, 0, Constants.WIDTH, Constants.HEIGHT);

            //Draw Frame
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);

            particleSystem.draw(g, canvas, 2, true);

            try {
                Thread.sleep(1000 / Constants.FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
