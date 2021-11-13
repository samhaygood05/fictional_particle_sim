package com.fictional_particle_sim.geometrics

class BoundingBox(var a: Point, var b: Point) {


    operator fun plus(b: BoundingBox): BoundingBox = BoundingBox(Point(a.x.coerceAtMost(b.a.x), a.y.coerceAtMost(b.a.y)), Point(this.b.x.coerceAtLeast(b.b.x), this.b.y.coerceAtLeast(b.b.y)))

    fun intersect(b: BoundingBox): Boolean = !(this.b.x < b.a.x || a.x > b.b.x || this.b.y < b.a.y || a.y > b.b.y)
}