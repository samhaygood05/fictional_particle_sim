package com.fictional_particle_sim

import com.fictional_particle_sim.geometrics.Point
import com.fictional_particle_sim.geometrics.Vector
import com.fictional_particle_sim.physicals.Shape.*
import com.fictional_particle_sim.physicals.Barrier
import com.fictional_particle_sim.physicals.Field
import com.fictional_particle_sim.physicals.Particle
import com.fictional_particle_sim.util.Constants
import com.fictional_particle_sim.util.Functions
import com.fictional_particle_sim.util.TheCanvas
import com.fictional_particle_sim.util.VectorSpace
import java.awt.Color
import java.awt.Graphics
import kotlin.math.PI

object Main {
    val f: Point.() -> Vector = { Vector.vector(r = 1.0, -PI / 2) }
    @JvmStatic
    fun main(args: Array<String>) {

        // Initializes Particle System
        Constants.particleSystem.particles = arrayOf(
                Particle(pos = Constants.SCALED_CENTER, charge = 1.0),
                Functions.random(),
                Functions.random(),
                Functions.random(),
                Functions.random()
        )
        Constants.particleSystem.barriers = arrayOf(
                Barrier(center = Point(Constants.SCALE_WIDTH / 2, Constants.SCALE_HEIGHT / 2), radius = 1.0, color = Color.CYAN, shape = CIRCLE), Barrier(topLeft = Point(Constants.SCALE_WIDTH / 2 - 1, Constants.SCALE_HEIGHT / 2 - 1), bottomRight = Point(Constants.SCALE_WIDTH / 2 + 1, Constants.SCALE_HEIGHT / 2 + 1), color = Color.CYAN, shape = RECTANGLE)
        )
        Constants.particleSystem.fields = arrayOf(
                Field(topLeft = Point(), bottomRight = Point(Constants.SCALE_WIDTH, Constants.SCALE_HEIGHT), color = Color(255, 0, 0, 10), fieldForce = f, shape = RECTANGLE)
        )
        val fr = VectorSpace(Constants.WIDTH, Constants.HEIGHT)
    }

    fun computeAndDraw(g: Graphics, canvas: TheCanvas?) {
        while (true) {

            //Compute Next Frame
            if (Constants.DEBUG) {
                println(Constants.particleSystem)
            }
            Constants.particleSystem.simulate()
            g.clearRect(0, 0, Constants.WIDTH, Constants.HEIGHT)

            //Draw Frame
            g.color = Color.BLACK
            g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT)
            Constants.particleSystem.draw(g, false)
            try {
                Thread.sleep((1000 / Constants.FPS).toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}