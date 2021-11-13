package com.fictional_particle_sim.physicals

import com.fictional_particle_sim.util.Constants
import com.fictional_particle_sim.util.EdgeBehavior.*
import com.fictional_particle_sim.geometrics.Point
import com.fictional_particle_sim.geometrics.Vector
import com.fictional_particle_sim.util.TheCanvas
import java.awt.Color
import java.awt.Graphics
import kotlin.math.abs

class ParticleSystem(var particles: Array<Particle> = arrayOf(), var barriers: Array<Barrier> = arrayOf(), var fields: Array<Field> = arrayOf()) {
    fun simulate() {
        for (particle in particles) {
            var force = Vector()
            var maxCharge = 0.0
            var charge = 0.0

            // Calculates all particle interactions
            for (agent in particles) {
                if (!particle.fixedVel) force += particle.force(agent)
                if (!particle.fixedCharge) {
                    if (!particle.fixedMaxCharge) {
                        maxCharge += particle.maxCharge(agent)
                        charge += particle.charge(agent)
                    } else {
                        val deltaCharge = particle.charge(agent)
                        if (abs(deltaCharge) / deltaCharge == abs(maxCharge) / maxCharge) {
                            charge += deltaCharge
                        }
                    }
                }
            }
            particle.applyAcc(particle.acc(force))
            particle.applyVel(true)
            force = Vector()

            // Calculates all field interactions
            for (field in fields) {
                if (particle.collide(field)) {
                    val fieldForce = field.fieldForce
                    force += particle.pos.fieldForce()
                    if (!particle.fixedVel) particle.vel = particle.vel * field.velocityScalar
                    if (!particle.fixedCharge) particle.charge = particle.charge * field.chargeScalar
                }
            }
            particle.applyAcc(particle.acc(force))
            particle.applyVel(false)

            if (!particle.fixedCharge) {
                if (abs(charge) > Constants.MAX_CHARGE) charge = Constants.MAX_CHARGE * charge / abs(charge)
                if (abs(maxCharge) > Constants.MAX_CHARGE) maxCharge = Constants.MAX_CHARGE * maxCharge / abs(maxCharge)
                particle.maxCharge = maxCharge
                if (abs(charge + particle.charge) < abs(maxCharge) && (charge + particle.charge) / abs(charge + particle.charge) == maxCharge / abs(maxCharge) || (charge + particle.charge) / abs(charge + particle.charge) != maxCharge / abs(maxCharge)) particle.charge = charge + particle.charge
            }
            particle.chargeColor()

            // Calculates all barrier interactions
            for (barrier in barriers) particle.collide(barrier)

            // Calculates screen edge interactions
            when (Constants.EDGE_BEHAVIOR) {
                LOOP -> {
                    if (particle.pos.x >= Constants.SCALE_WIDTH) {
                        particle.pos.x = particle.pos.x % Constants.SCALE_WIDTH
                    } else if (particle.pos.x < 0) {
                        particle.pos.x = particle.pos.x % Constants.SCALE_WIDTH + Constants.SCALE_WIDTH
                    }
                    if (particle.pos.y >= Constants.SCALE_HEIGHT) {
                        particle.pos.y = particle.pos.y % Constants.SCALE_HEIGHT
                    } else if (particle.pos.y < 0) {
                        particle.pos.y = particle.pos.y % Constants.SCALE_HEIGHT + Constants.SCALE_HEIGHT
                    }
                }
                BORDER -> {
                    if (particle.pos.x >= Constants.SCALE_WIDTH) {
                        particle.vel = Vector(Point(-particle.vel.center().end.x, particle.vel.center().end.y))
                        particle.pos = Point(Constants.SCALE_WIDTH - 0.001, particle.pos.y)
                    } else if (particle.pos.x < 0) {
                        particle.vel = Vector(Point(-particle.vel.center().end.x, particle.vel.center().end.y))
                        particle.pos = Point(0.001, particle.pos.y)
                    }
                    if (particle.pos.y >= Constants.SCALE_HEIGHT) {
                        particle.vel = Vector(Point(particle.vel.center().end.x, -particle.vel.center().end.y))
                        particle.pos = Point(particle.pos.x, Constants.SCALE_HEIGHT - 0.001)
                    } else if (particle.pos.y < 0) {
                        particle.vel = Vector(Point(particle.vel.center().end.x, -particle.vel.center().end.y))
                        particle.pos = Point(particle.pos.x, 0.001)
                    }
                }
                NOTHING -> {}
            }
        }
    }
    fun draw(g: Graphics, showVel: Boolean) {
        if (fields.isNotEmpty()) {
            TheCanvas.drawFields(g, fields)
        }
        if (barriers.isNotEmpty()) {
            TheCanvas.drawBarriers(g, barriers)
        }
        if (particles.isNotEmpty()) {
            for (particle in particles) {
                TheCanvas.drawPoint(g, particle.pos * (Constants.PPU.toDouble()), (Constants.MIN_DIST * Constants.PPU / 2).toInt(), Color(particle.chargeColor.red, particle.chargeColor.green, particle.chargeColor.blue, 130))
                TheCanvas.drawPoint(g, particle.pos * (Constants.PPU.toDouble()), (Constants.MIN_DIST * Constants.PPU / 8).toInt(), particle.maxChargeColor)
                if (showVel) {
                    TheCanvas.drawVector(g, particle.vel.scaleFromOrigin(100.0 / Constants.PPU).center(particle.pos * (Constants.PPU.toDouble())), true, .1, .05, particle.chargeColor)
                }
            }
        }
    }
}

