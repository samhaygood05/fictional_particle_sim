package com.fictional_particle_sim.geometrics

import kotlin.math.round

fun Point(x: Int, y: Int): Point = Point(x.toDouble(), y.toDouble())
class Point(var x: Double = 0.0, var y: Double = 0.0) {

    operator fun plus(that: Point) = Point(this.x + that.x, this.y + that.y)

    operator fun minus(that: Point) = Point(this.x - that.x, this.y - that.y)

    operator fun times(that: Double) = Point(this.x * that, this.y * that)
    operator fun Double.times(that: Point) = that * this

    operator fun div(that: Double) = this * (1.0/that)

    fun dist(that: Point = Point()) = Vector(this, that).magnitude()
    fun disp(that: Vector) = Vector(that.start, this).perp(that)

    fun inside(k: BoundingBox): Boolean = x <= k.a.x.coerceAtLeast(k.b.x) && x >= k.a.x.coerceAtMost(k.b.x) && y <= k.a.y.coerceAtLeast(k.b.y) && y >= k.a.y.coerceAtMost(k.b.y)

    override fun toString(): String = "( ${round(x * 10000) / 10000} , ${round(x * 10000) / 10000} )"

}