package com.fictional_particle_sim.physicals

import com.fictional_particle_sim.util.Constants
import com.fictional_particle_sim.util.EdgeBehavior.*
import com.fictional_particle_sim.util.Graphics.*
import com.fictional_particle_sim.geometrics.Point
import com.fictional_particle_sim.geometrics.Vector
import com.fictional_particle_sim.util.Constants.Companion.GRAPHICS
import com.fictional_particle_sim.util.Constants.Companion.MAX_CHARGE
import com.fictional_particle_sim.util.Constants.Companion.BARRIER_DEBUG
import com.fictional_particle_sim.util.Constants.Companion.FIELD_DEBUG
import com.fictional_particle_sim.util.Constants.Companion.HEIGHT
import com.fictional_particle_sim.util.Constants.Companion.PARTICLE_DEBUG
import com.fictional_particle_sim.util.Constants.Companion.PPU
import com.fictional_particle_sim.util.Constants.Companion.SHOW_FPS
import com.fictional_particle_sim.util.Constants.Companion.SHOW_TRAILS
import com.fictional_particle_sim.util.Constants.Companion.SPF
import com.fictional_particle_sim.util.Constants.Companion.WIDTH
import com.fictional_particle_sim.util.TheCanvas
import java.awt.Color
import java.awt.Graphics
import kotlin.math.*

class ParticleSystem(var particles: Array<Particle> = arrayOf(), var barriers: Array<Barrier> = arrayOf(), var fields: Array<Field> = arrayOf(), var frame: Long = 0) {
    fun simulate() {
        frame++
        for (particle in particles) {
            val tempMaxCharge = when (particle.type) {
                Type.UNIVERSAL_ATTRACTOR, Type.UNIVERSAL_REPELLER -> Constants.MAX_ATTRACTOR_CHARGE
                Type.CHARGED -> MAX_CHARGE
            }
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
                    val velocityScalar = field.velocityScalar
                    val chargeScalar = field.chargeScalar
                    force += particle.pos.fieldForce()
                    if (!particle.fixedVel) particle.vel = particle.vel * (((particle.pos.velocityScalar() - 1) * SPF) + 1)
                    if (!particle.fixedCharge) particle.charge = particle.charge * (((particle.pos.chargeScalar() - 1) * SPF) + 1)
                }
            }
            particle.applyAcc(particle.acc(force))
            particle.applyVel(false)

            if (!particle.fixedCharge) {
                if (abs(charge) > tempMaxCharge) charge = tempMaxCharge * sign(charge)
                if (abs(maxCharge) > tempMaxCharge) maxCharge = tempMaxCharge * sign(maxCharge)
                particle.targetCharge = maxCharge
                if (abs(charge + particle.charge) < abs(maxCharge) && sign(charge + particle.charge) == sign(charge) || sign(charge + particle.charge) != sign(charge)) particle.charge = charge + particle.charge
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
                        particle.vel = Vector(Point(particle.vel.x(), -particle.vel.y()))
                        particle.pos = Point(Constants.SCALE_WIDTH - 0.001, particle.pos.y)
                    } else if (particle.pos.x < 0) {
                        particle.vel = Vector(Point(particle.vel.x(), -particle.vel.y()))
                        particle.pos = Point(0.001, particle.pos.y)
                    }
                    if (particle.pos.y >= Constants.SCALE_HEIGHT) {
                        particle.vel = Vector(Point(-particle.vel.x(), particle.vel.y()))
                        particle.pos = Point(particle.pos.x, Constants.SCALE_HEIGHT - 0.001)
                    } else if (particle.pos.y < 0) {
                        particle.vel = Vector(Point(-particle.vel.x(), particle.vel.y()))
                        particle.pos = Point(particle.pos.x, 0.001)
                    }
                }
                NOTHING -> {}
            }
        }
    }
    fun draw(g: Graphics, showVel: Boolean) {
        if (!SHOW_TRAILS) {
            g.color = Color.BLACK
            g.fillRect(0, 0, WIDTH, HEIGHT)
            if (SHOW_FPS){
                g.color = Color.BLACK
                g.fillRect(0, 0, 55, 15)
            }
        }
        if (fields.isNotEmpty()) {
            TheCanvas.drawFields(g, fields)
        }
        if (barriers.isNotEmpty()) {
            TheCanvas.drawBarriers(g, barriers)
        }
        if (particles.isNotEmpty()) {
            for (particle in particles) {
                TheCanvas.drawPoint(g, particle.pos * (PPU.toDouble()), (Constants.MIN_DIST * PPU / 2).toInt(), when (GRAPHICS){
                    FAST -> Color(particle.chargeColor.red*130/255, particle.chargeColor.green*130/255, particle.chargeColor.blue*130/255)
                    FANCY -> Color(particle.chargeColor.red, particle.chargeColor.green, particle.chargeColor.blue, 130)
                })

            }
            if (PPU > 25){
                for (particle in particles) {
                    TheCanvas.drawPoint(
                        g,
                        particle.pos * (PPU.toDouble()),
                        (Constants.MIN_DIST * abs(particle.mass).pow(1 / 4.0) * PPU / 8).toInt(),
                        particle.targetChargeColor
                    )
                    if (showVel) {
                        TheCanvas.drawVector(
                            g,
                            particle.vel.scaleFromOrigin(100.0 / PPU)
                                .center(particle.pos * (PPU.toDouble())),
                            true,
                            .1,
                            .05,
                            particle.chargeColor
                        )
                    }
                }
            }
        }
        if (SHOW_FPS) {
            val elapsedTime: Double = System.currentTimeMillis() / 1000.0 - Constants.begin
            if (frame / elapsedTime <= 20) g.color = Color.RED else g.color = Color.WHITE
            g.drawString("FPS: ${(frame / elapsedTime).roundToInt()}", 2, 13)
        }
    }

    override fun toString(): String {
        var temp = ""
        if (PARTICLE_DEBUG) {
            temp += "Particles:\n"
            for (particle in particles.indices) {
                temp += "$particle: ${particles[particle]}\n"
            }
        }
        if (BARRIER_DEBUG) {
            temp += "\nBarriers:\n"
            for (barrier in barriers.indices) {
                temp += "$barrier: ${barriers[barrier]}\n"
            }
        }
        if (FIELD_DEBUG) {
            temp += "\nFields:\n"
            for (field in fields.indices) {
                temp += "$field: ${fields[field]}\n"
            }
        }
        return temp
    }
}

