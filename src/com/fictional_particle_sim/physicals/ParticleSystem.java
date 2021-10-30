package com.fictional_particle_sim.physicals;

import com.fictional_particle_sim.TheCanvas;
import com.fictional_particle_sim.geometrics.*;
import com.fictional_particle_sim.geometrics.Point;

import java.awt.*;

import static com.fictional_particle_sim.Constants.*;

public class ParticleSystem {
    public Particle[] particles;
    public Barrier[] barriers;

    public void simulate() {
        for (Particle particle : particles) {
            Vector force = new Vector();
            double maxCharge = 0;
            double charge = 0;

            // Calculates All Particle Interactions
            for (Particle agent : particles) {
                if (!particle.fixedVel) {
                    force = force.add(particle.force(agent));
                }
                if (!particle.fixedCharge) {
                    if (!particle.fixedMaxCharge){
                        maxCharge += particle.maxCharge(agent);
                        charge += particle.charge(agent);
                    } else {
                        double deltaCharge = particle.charge(agent);
                        if (Math.abs(deltaCharge)/deltaCharge == Math.abs(maxCharge)/maxCharge) {
                            charge += deltaCharge;
                        }
                    }
                }
            }
            particle.applyAcc(particle.acc(force));
            particle.applyVel();
            if (!particle.fixedCharge) {
                if (Math.abs(charge) > MAX_CHARGE) charge = MAX_CHARGE * charge/Math.abs(charge);
                if (Math.abs(maxCharge) > MAX_CHARGE) maxCharge = MAX_CHARGE * maxCharge/Math.abs(maxCharge);
                particle.setMaxCharge(maxCharge);
                if ((Math.abs(charge + particle.charge) < Math.abs(maxCharge) && (charge + particle.charge)/Math.abs(charge + particle.charge) == maxCharge/Math.abs(maxCharge)) || (charge + particle.charge)/Math.abs(charge + particle.charge) != maxCharge/Math.abs(maxCharge))
                    particle.setCharge(charge + particle.charge);
            }
            particle.chargeColor();

            //Calculates All Barrier Interactions
            for (Barrier barrier : barriers) {
                particle.collide(barrier);
            }

            //Loops the Particle to Other Side of Screen
            switch (EDGE_BEHAVIOR) {
                case "LOOP" : {
                    if (particle.pos.x >= SCALE_WIDTH) {
                        particle.pos.x = particle.pos.x % SCALE_WIDTH;
                    } else if (particle.pos.x < 0) {
                        particle.pos.x = (particle.pos.x % SCALE_WIDTH) + SCALE_WIDTH;
                    }
                    if (particle.pos.y >= SCALE_HEIGHT) {
                        particle.pos.y = particle.pos.y % SCALE_HEIGHT;
                    } else if (particle.pos.y < 0) {
                        particle.pos.y = (particle.pos.y % SCALE_HEIGHT) + SCALE_HEIGHT;
                    }
                }
                case "BORDER" : {
                    if (particle.pos.x >= SCALE_WIDTH) {
                        particle.vel = new Vector (new Point(-particle.vel.center().end.x, particle.vel.center().end.y));
                        particle.pos = new Point(SCALE_WIDTH - 0.001, particle.pos.y);
                    } else if (particle.pos.x < 0) {
                        particle.vel = new Vector (new Point(-particle.vel.center().end.x, particle.vel.center().end.y));
                        particle.pos = new Point(0.001, particle.pos.y);
                    }
                    if (particle.pos.y >= SCALE_HEIGHT) {
                        particle.vel = new Vector (new Point(particle.vel.center().end.x, -particle.vel.center().end.y));
                        particle.pos = new Point(particle.pos.x, SCALE_HEIGHT - 0.001);
                    } else if (particle.pos.y < 0) {
                        particle.vel = new Vector (new Point(particle.vel.center().end.x, -particle.vel.center().end.y));
                        particle.pos = new Point(particle.pos.x, 0.001);

                    }
                }
            }

        }
    }
    public void draw(Graphics g, TheCanvas canvas, boolean showVel) {
        if (barriers.length > 0) {

            TheCanvas.drawBarriers(g, barriers);
        }
        if (particles.length > 0){
            for (Particle particle : particles) {
                TheCanvas.drawPoint(g, particle.pos.mult(PPU), (int) (MIN_DIST * PPU / 2), new Color(particle.chargeColor.getRed(), particle.chargeColor.getGreen(), particle.chargeColor.getBlue(), 130));
                TheCanvas.drawPoint(g, particle.pos.mult(PPU), (int) (MIN_DIST * PPU / 8), particle.maxChargeColor);
                if (showVel) {
                    TheCanvas.drawVector(g, particle.vel.scaleFromOrigin(100. / PPU).center(particle.pos.mult(PPU)), true, .1, .05, particle.chargeColor);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Particle particle : particles) {
            string.append(particle).append("\n");
        }
        return string.toString();
    }
}
