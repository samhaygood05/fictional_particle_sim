package com.fictional_particle_sim

import com.fictional_particle_sim.geometrics.Point
import com.fictional_particle_sim.geometrics.Vector
import com.fictional_particle_sim.physicals.Shape.*
import com.fictional_particle_sim.physicals.Field
import com.fictional_particle_sim.physicals.Particle
import com.fictional_particle_sim.util.Constants
import com.fictional_particle_sim.util.VectorSpace
import java.awt.Color
import java.awt.Graphics

object Main {
    val r: Double = 0.01
    val f: Point.() -> Vector = { Vector(end = Point(-r * (x - Constants.SCALED_CENTER.x) / (this - Constants.SCALED_CENTER).dist() , -r * (y - Constants.SCALED_CENTER.y) / (this - Constants.SCALED_CENTER).dist())) }
    @JvmStatic
    fun main(args: Array<String>) {

        // Initializes Particle System
        Constants.particleSystem.particles = arrayOf(
                Particle(pos = Constants.SCALED_CENTER - Point(1,0), vel = Vector(end = Point(0, 7))),
                Particle(pos = Constants.SCALED_CENTER + Point(1,0), vel = Vector(end = Point(0, -7)))
        )
        Constants.particleSystem.barriers = arrayOf(
        )
        Constants.particleSystem.fields = arrayOf(
                Field(topLeft = Point(), bottomRight = Point(Constants.SCALE_WIDTH, Constants.SCALE_HEIGHT), color = Color(255, 0, 0, 0), fieldForce = f, velocityScalar = 0.9, shape = RECTANGLE)
        )
        val fr = VectorSpace(Constants.WIDTH, Constants.HEIGHT)
    }

    fun computeAndDraw(g: Graphics) {
        while (true) {

            //Compute Next Frame
            if (Constants.DEBUG) {
                println(Constants.particleSystem)
            }
            Constants.particleSystem.simulate()

            //Draw Frame
            g.color = Color.BLACK
            g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT)
            Constants.particleSystem.draw(g, false)
            Thread.sleep((1000 / Constants.FPS).toLong())
        }
    }
}