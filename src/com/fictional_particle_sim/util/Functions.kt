package com.fictional_particle_sim.util

import com.fictional_particle_sim.geometrics.Point
import com.fictional_particle_sim.geometrics.Vector
import com.fictional_particle_sim.physicals.Particle
import com.fictional_particle_sim.util.Constants.Companion.MAX_CHARGE

object Functions {

    fun random(): Particle {
        return Particle(pos = Point(Math.random() * Constants.SCALE_WIDTH, Math.random() * Constants.SCALE_HEIGHT), charge = (Math.random() * 2 - 1) * MAX_CHARGE)
    }
    fun random(mass: Double): Particle {
        return Particle(pos = Point(Math.random() * Constants.SCALE_WIDTH, Math.random() * Constants.SCALE_HEIGHT), mass = mass, charge = (Math.random() * 2 - 1) * MAX_CHARGE)
    }
    fun random(vel: Vector, mass: Double): Particle {
        return Particle(pos = Point(Math.random() * Constants.SCALE_WIDTH, Math.random() * Constants.SCALE_HEIGHT), vel = vel, mass = mass, charge = (Math.random() * 2 - 1) * MAX_CHARGE)
    }
    fun random(mass: Double = 1.0, charge: Double): Particle {
        return Particle(pos = Point(Math.random() * Constants.SCALE_WIDTH, Math.random() * Constants.SCALE_HEIGHT), mass = mass, charge = charge)
    }
    fun random(vel: Vector, mass: Double, charge: Double): Particle {
        return Particle(pos = Point(Math.random() * Constants.SCALE_WIDTH, Math.random() * Constants.SCALE_HEIGHT), vel = vel, mass = mass, charge = charge)
    }
    fun random(mass: Double, charge: Double, fixedCharge: Boolean, fixedMaxCharge: Boolean, fixedVel: Boolean): Particle {
        return Particle(pos = Point(Math.random() * Constants.SCALE_WIDTH, Math.random() * Constants.SCALE_HEIGHT), mass = mass, charge = charge, fixedCharge = fixedCharge, fixedMaxCharge = fixedMaxCharge, fixedVel = fixedVel)
    }
    fun random(vel: Vector, mass: Double, charge: Double, fixedCharge: Boolean, fixedMaxCharge: Boolean, fixedVel: Boolean): Particle {
        return Particle(pos = Point(Math.random() * Constants.SCALE_WIDTH, Math.random() * Constants.SCALE_HEIGHT), vel = vel, mass = mass, charge = charge, fixedCharge = fixedCharge, fixedMaxCharge = fixedMaxCharge, fixedVel = fixedVel)
    }
}