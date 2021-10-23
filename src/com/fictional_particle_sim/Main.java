package com.fictional_particle_sim;

import com.fictional_particle_sim.geometrics.Point;
import com.fictional_particle_sim.geometrics.Vector;
import com.fictional_particle_sim.physicals.Barrier;
import com.fictional_particle_sim.physicals.Particle;

import java.awt.*;

import static com.fictional_particle_sim.Constants.*;


public class Main {

    public static void main(String[] args) {

        // Initializes Particle System
        particleSystem.particles = new Particle[] {
                new Particle(new Point(SCALE_WIDTH/2, SCALE_HEIGHT/2), new Vector(100,Math.PI))
        };
        particleSystem.barriers = new Barrier[] {
                new Barrier(new Vector(new Point(SCALE_WIDTH/2 - 1, SCALE_HEIGHT/2 - 2), new Point(SCALE_WIDTH/2 - 2, SCALE_HEIGHT/2 + 2)))
        };
        particleSystem.barriers[0].line.color = Color.CYAN;

        VectorSpace fr = new VectorSpace(Constants.WIDTH, Constants.HEIGHT);

    }

    public static void computeAndDraw(Graphics g, TheCanvas canvas) {
        while (true){

            //Compute Next Frame
            if (DEBUG){
                System.out.println(particleSystem);
            }

            particleSystem.simulate(BORDER);

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
