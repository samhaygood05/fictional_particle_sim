package com.fictional_particle_sim.geometrics

class Point(var x: Double = 0.0, var y: Double = 0.0) {

    operator fun plus(that: Point) = Point(this.x + that.x, this.y + that.y)

    operator fun minus(that: Point) = Point(this.x - that.x, this.y - that.y)

    operator fun times(that: Double) = Point(this.x * that, this.y * that)
    operator fun Double.times(that: Point) = that * this

    operator fun div(that: Double) = this * (1.0/that)

    fun dist(that: Point) = Vector(this, that).magnitude()
    fun disp(that: Vector) = Vector(that.start, this).perp(that)

    fun inside(k: BoundingBox): Boolean = x <= Math.max(k.a.x, k.b.x) && x >= Math.min(k.a.x, k.b.x) && y <= Math.max(k.a.y, k.b.y) && y >= Math.min(k.a.y, k.b.y)

    override fun toString(): String = "( $x , $y )"

}