package com.fictional_particle_sim

import com.fictional_particle_sim.geometrics.Point
import com.fictional_particle_sim.geometrics.Vector
import com.fictional_particle_sim.physicals.Shape.*
import com.fictional_particle_sim.physicals.Field
import com.fictional_particle_sim.util.Functions
import com.fictional_particle_sim.physicals.attractiveField
import com.fictional_particle_sim.util.Constants
import com.fictional_particle_sim.util.Constants.Companion.SHOW_FPS
import com.fictional_particle_sim.util.Constants.Companion.particleSystem
import com.fictional_particle_sim.util.VectorSpace
import com.fictional_particle_sim.util.vectorField
import java.awt.Color
import java.awt.Graphics
import kotlin.math.roundToInt

object Main {
    private val f: vectorField = attractiveField(Constants.SCALED_CENTER, 0.1)
    @JvmStatic
    fun main(args: Array<String>) {

        // Initializes Particle System
        Constants.particleSystem.particles = arrayOf(
                Functions.random(charge = 5.0),
                Functions.random(charge = 5.0),
                Functions.random(charge = 5.0),
                Functions.random(charge = -5.0),
                Functions.random(charge = -5.0),
                Functions.random(charge = -5.0)
        )
        Constants.particleSystem.barriers = arrayOf(
        )
        Constants.particleSystem.fields = arrayOf(
                Field(topLeft = Point(), bottomRight = Point(Constants.SCALE_WIDTH, Constants.SCALE_HEIGHT), color = Color(0,0,0,0), fieldForce = {Vector()}, velocityScalar = { 0.9 }, shape = RECTANGLE)
        )
        val fr = VectorSpace(Constants.WIDTH, Constants.HEIGHT)
    }

    fun computeAndDraw(g: Graphics) {
        g.color = Color.BLACK
        g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT)
        while (true) {

            //Draw Frame
            g.color = Color.BLACK
            g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT)
            particleSystem.draw(g, false)
            Thread.sleep((1000 / Constants.FPS).toLong())
            if (SHOW_FPS) {
                val elapsedTime: Double = System.currentTimeMillis() / 1000.0 - Constants.begin
                if (particleSystem.frame / elapsedTime <= 20) g.color = Color.RED else g.color = Color.WHITE
                g.drawString("FPS: ${(particleSystem.frame / elapsedTime).roundToInt()}", 5, 10)
            }

            //Compute Next Frame
            if (Constants.DEBUG) println(particleSystem)
            particleSystem.simulate()

        }
    }
}