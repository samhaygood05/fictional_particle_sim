package com.fictional_particle_sim;

import com.fictional_particle_sim.geometrics.Point;
import com.fictional_particle_sim.geometrics.Vector;
import com.fictional_particle_sim.physicals.Barrier;
import com.fictional_particle_sim.physicals.Field;
import com.fictional_particle_sim.physicals.Particle;

import java.awt.*;

import static com.fictional_particle_sim.Constants.*;


public class Main {

    public static void main(String[] args) {

        // Initializes Particle System
        particleSystem.particles = new Particle[] {
                new Particle(new Point(SCALE_WIDTH/2, SCALE_HEIGHT/2), 1.0, 1.0),
                Particle.random(),
                Particle.random(),
                Particle.random(),
                Particle.random(),
                Particle.random(),
                Particle.random(),
                Particle.random(),
                Particle.random()
        };
        particleSystem.barriers = new Barrier[] {
                new Barrier(new Point(SCALE_WIDTH/2, SCALE_HEIGHT/2), 1, Color.CYAN)
                , new Barrier(new Point(SCALE_WIDTH/2 - 1, SCALE_HEIGHT/2 - 1), new Point(SCALE_WIDTH/2 + 1, SCALE_HEIGHT/2 + 1), Color.CYAN)
        };
        VectorSpace fr = new VectorSpace(Constants.WIDTH, Constants.HEIGHT);
        particleSystem.fields = new Field[] {
            new Field(new Point(), new Point(SCALE_WIDTH, SCALE_HEIGHT), new Color(255, 0, 0, 10), new Vector(new Point(0, 0.1)))
        };

    }

    public static void computeAndDraw(Graphics g, TheCanvas canvas) {
        while (true){

            //Compute Next Frame
            if (DEBUG){
                System.out.println(particleSystem);
            }

            particleSystem.simulate();

            g.clearRect(0, 0, Constants.WIDTH, Constants.HEIGHT);

            //Draw Frame
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);

            particleSystem.draw(g, canvas, false);

            try {
                Thread.sleep(1000 / Constants.FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
