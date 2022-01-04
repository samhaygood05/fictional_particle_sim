package com.fictional_particle_sim

import com.fictional_particle_sim.geometrics.Point
import com.fictional_particle_sim.physicals.Shape.*
import com.fictional_particle_sim.physicals.Field
import com.fictional_particle_sim.physicals.Particle
import com.fictional_particle_sim.physicals.Type.*
import com.fictional_particle_sim.util.Functions
import com.fictional_particle_sim.physicals.attractiveField
import com.fictional_particle_sim.util.Constants
import com.fictional_particle_sim.util.Constants.Companion.SCALED_CENTER
import com.fictional_particle_sim.util.Constants.Companion.SHOW_TRAILS
import com.fictional_particle_sim.util.Constants.Companion.particleSystem
import com.fictional_particle_sim.util.VectorSpace
import java.awt.Color
import java.awt.Graphics

object Main {
    @JvmStatic
    fun main(args: Array<String>) {

        // Initializes Particle System
        particleSystem.particles = Functions.randomParticleArray(320)
        particleSystem.barriers = arrayOf(
        )
        particleSystem.fields = arrayOf(
                Field(topLeft = Point(), bottomRight = Point(Constants.SCALE_WIDTH, Constants.SCALE_HEIGHT), color = Color(0,0,0,0), fieldForce = attractiveField(SCALED_CENTER, 0.1), velocityScalar = { 1.0 }, shape = RECTANGLE)
        )
        val fr = VectorSpace(Constants.WIDTH, Constants.HEIGHT)
    }

    fun computeAndDraw(g: Graphics) {
        g.color = Color.BLACK
        g.fillRect(0, 0, Constants.WIDTH*2, Constants.HEIGHT)
        while (true) {

            //Draw Frame
            //Thread.sleep(1)
            particleSystem.draw(g, false)

            //Compute Next Frame
            if (Constants.DEBUG) println(particleSystem)
            particleSystem.simulate()

        }
    }
}