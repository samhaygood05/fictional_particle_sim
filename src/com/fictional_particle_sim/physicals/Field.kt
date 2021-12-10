package com.fictional_particle_sim.physicals

import com.fictional_particle_sim.Main
import com.fictional_particle_sim.geometrics.BoundingBox
import com.fictional_particle_sim.geometrics.Point
import com.fictional_particle_sim.geometrics.Vector
import com.fictional_particle_sim.physicals.Shape.*
import com.fictional_particle_sim.util.scalarField
import com.fictional_particle_sim.util.vectorField
import java.awt.Color
import kotlin.math.pow

class Field(var topLeft: Point = Point(), var bottomRight: Point = Point(), var center: Point = Point(), var radius: Double = 0.0, var velocityScalar: scalarField = { 1.0 }, var chargeScalar: scalarField = { 1.0 }, var fieldForce: vectorField = {Vector()}, var shape: Shape, var color: Color) {

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
            else -> {}
        }
        return BoundingBox(a, b)
    }

    override fun toString(): String {
        return when (shape) {
            RECTANGLE -> "$shape, $topLeft, $bottomRight, ${scalarFieldToString(velocityScalar)}, ${scalarFieldToString(chargeScalar)}, ${vectorFieldToString(fieldForce)}"
            CIRCLE -> "$shape, $center, $radius, ${scalarFieldToString(velocityScalar)}, ${scalarFieldToString(chargeScalar)}, ${vectorFieldToString(fieldForce)}"
            else -> ""
        }
    }
}

fun attractiveField(center: Point, strength: Double): vectorField = {
    Vector(this, center).norm() * strength * (Vector(this, center).magnitude()).pow(2)
}

fun vectorFieldToString(fieldForce: vectorField): String {
    return "Pos -> Vel"
}
fun scalarFieldToString(fieldForce: scalarField): String {
    return "Pos -> Scalar"
}
