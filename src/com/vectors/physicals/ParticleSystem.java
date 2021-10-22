package com.vectors.physicals;

import com.vectors.TheCanvas;
import com.vectors.geometrics.*;

import java.awt.*;

import static com.vectors.Constants.*;

public class ParticleSystem {
    public Particle[] particles;

    public void simulate(boolean loop) {
        for (Particle particle : particles) {
            Vector force = new Vector();
            double maxCharge = 0;
            double charge = 0;
            for (Particle agent : particles) {
                if (!particle.fixedVel) {
                    force = force.add(particle.force(agent));
                }
                if (!particle.fixedCharge) {
                    maxCharge += particle.maxCharge(agent);
                    charge += particle.charge(agent);
                }
            }
            particle.applyAcc(particle.acc(force));
            particle.applyVel();
            if (loop) {
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
            if (!particle.fixedCharge) {
                if (Math.abs(charge) > 1) charge = charge/Math.abs(charge);
                if (Math.abs(maxCharge) > 1) maxCharge = maxCharge/Math.abs(maxCharge);
                particle.setMaxCharge(maxCharge);
                if ((Math.abs(charge + particle.charge) < Math.abs(maxCharge) && (charge + particle.charge)/Math.abs(charge + particle.charge) == maxCharge/Math.abs(maxCharge)) || (charge + particle.charge)/Math.abs(charge + particle.charge) != maxCharge/Math.abs(maxCharge))
                    particle.setCharge(charge + particle.charge);
            }
            particle.chargeColor();
        }
    }
    public void draw(Graphics g, TheCanvas canvas, int r, boolean showVel) {
        for (Particle particle : particles) {
            TheCanvas.drawPoint(g, particle.pos.mult(PPU), r, particle.color);
            TheCanvas.drawPoint(g, particle.pos.mult(PPU), (int)(MIN_DIST*PPU/2), new Color(particle.color.getRed(), particle.color.getGreen(), particle.color.getBlue(), 100));
            if (showVel) {
                TheCanvas.drawVector(g, particle.vel.scaleFromOrigin(100. / PPU).center(particle.pos.mult(PPU)), true, .1, .05, particle.color);
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
