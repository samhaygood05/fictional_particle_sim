package com.fictional_particle_sim.physicals

import com.fictional_particle_sim.geometrics.BoundingBox
import com.fictional_particle_sim.geometrics.Point
import com.fictional_particle_sim.geometrics.Vector
import com.fictional_particle_sim.physicals.Shape.*
import java.awt.Color

class Barrier(var line: Vector = Vector(), var topLeft: Point = Point(), var bottomRight: Point = Point(), var center: Point = Point(), var radius: Double = 0.0, var shape: Shape, var color: Color = Color.CYAN) {


    fun boundingBox(): BoundingBox {
        val a: Point
        val b: Point
        when (shape) {
                LINE -> {
                    a = line.boundingBox().a
                    b = line.boundingBox().b
                }
                CIRCLE -> {
                    a = center - Point(radius, radius)
                    b = center + Point(radius, radius)
                }
                RECTANGLE -> {
                    a = topLeft
                    b = bottomRight
                }
            }
        return BoundingBox(a, b)
        }

    override fun toString(): String {
        return when (shape) {
            LINE -> "$shape, ${line.start}, ${line.end}"
            RECTANGLE -> "$shape, $topLeft, $bottomRight"
            CIRCLE -> "$shape, $center, $radius"
        }
    }
}


enum class Shape {
    LINE, CIRCLE, RECTANGLE
}