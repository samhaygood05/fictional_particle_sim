package com.fictional_particle_sim.util

import com.fictional_particle_sim.geometrics.Point
import com.fictional_particle_sim.geometrics.Vector
import com.fictional_particle_sim.physicals.Particle
import com.fictional_particle_sim.physicals.Type
import com.fictional_particle_sim.physicals.Type.*
import com.fictional_particle_sim.util.Constants.Companion.MAX_CHARGE
import kotlin.math.floor
import kotlin.math.roundToInt

object Functions {

    fun random(vel: Vector = Vector(), mass: Double = 1.0, fixedCharge: Boolean = false, fixedMaxCharge: Boolean = false, fixedVel: Boolean = false, type: Type = CHARGED): Particle {
        val tempMaxCharge = when (type) {
            UNIVERSAL_ATTRACTOR, UNIVERSAL_REPELLER -> Constants.MAX_ATTRACTOR_CHARGE
            CHARGED -> MAX_CHARGE
        }
        return Particle(pos = Point(Math.random() * Constants.SCALE_WIDTH, Math.random() * Constants.SCALE_HEIGHT), vel = vel, mass = mass, charge = (Math.random() * 2 - 1) * tempMaxCharge, fixedCharge = fixedCharge, fixedMaxCharge = fixedMaxCharge, fixedVel = fixedVel, type = type)
    }
    fun random(vel: Vector = Vector(), mass: Double = 1.0, charge: Double, fixedCharge: Boolean = false, fixedMaxCharge: Boolean = false, fixedVel: Boolean = false, type: Type = CHARGED): Particle {
        return Particle(pos = Point(Math.random() * Constants.SCALE_WIDTH, Math.random() * Constants.SCALE_HEIGHT), vel = vel, mass = mass, charge = charge, fixedCharge = fixedCharge, fixedMaxCharge = fixedMaxCharge, fixedVel = fixedVel, type = type)
    }
    fun random(vel: Vector = Vector(), mass: Double = 1.0, fixedCharge: Boolean = false, fixedMaxCharge: Boolean = false, fixedVel: Boolean = false): Particle {
        val type: Type = when (floor(Math.random() * 3).toInt()) {
            0 -> CHARGED
            else -> when (floor(Math.random() * 2).toInt()) {
                0 -> UNIVERSAL_ATTRACTOR
                else -> UNIVERSAL_REPELLER
            }
        }
        val tempMaxCharge = when (type) {
            UNIVERSAL_ATTRACTOR, UNIVERSAL_REPELLER -> Constants.MAX_ATTRACTOR_CHARGE
            CHARGED -> MAX_CHARGE
        }
        return Particle(pos = Point(Math.random() * Constants.SCALE_WIDTH, Math.random() * Constants.SCALE_HEIGHT), vel = vel, mass = mass, charge = (Math.random() * 2 - 1) * tempMaxCharge, fixedCharge = fixedCharge, fixedMaxCharge = fixedMaxCharge, fixedVel = fixedVel, type = type)
    }
    fun random(vel: Vector = Vector(), mass: Double = 1.0, charge: Double, fixedCharge: Boolean = false, fixedMaxCharge: Boolean = false, fixedVel: Boolean = false): Particle {
        val type: Type = when (floor(Math.random() * 3).toInt()) {
            0 -> CHARGED
            1 -> UNIVERSAL_ATTRACTOR
            else -> UNIVERSAL_REPELLER
        }
        return Particle(pos = Point(Math.random() * Constants.SCALE_WIDTH, Math.random() * Constants.SCALE_HEIGHT), vel = vel, mass = mass, charge = charge, fixedCharge = fixedCharge, fixedMaxCharge = fixedMaxCharge, fixedVel = fixedVel, type = type)
    }
    fun randomParticleArray(n: Int): Array<Particle> {
        val particles: MutableList<Particle> = mutableListOf()
        for (i in 1..n) {
            particles.add(random())
        }
        return particles.toTypedArray()
    }
    fun nGluonicParticlePairs(n: Int): Array<Particle> {
        val particles: MutableList<Particle> = mutableListOf()
        for (i in 1..n) {
            particles.add(random(type = UNIVERSAL_ATTRACTOR))
            particles.add(random(type = UNIVERSAL_REPELLER))
        }
        return particles.toTypedArray()
    }
}