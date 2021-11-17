package com.fictional_particle_sim.physicals

import com.fictional_particle_sim.geometrics.BoundingBox
import com.fictional_particle_sim.geometrics.Point
import com.fictional_particle_sim.geometrics.Vector
import com.fictional_particle_sim.physicals.Shape.*
import java.awt.Color

class Field(var topLeft: Point = Point(), var bottomRight: Point = Point(), var center: Point = Point(), var radius: Double = 0.0, var velocityScalar: Double = 1.0, var chargeScalar: Double = 1.0, var fieldForce: Point.() -> Vector, var shape: Shape, var color: Color) {

    fun boundingBox(): BoundingBox {
        var a = Point()
        var b = Point()
        when (shape) {
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
}