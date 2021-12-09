package com.fictional_particle_sim.geometrics

import java.awt.Color
import kotlin.math.*

class Vector(var start: Point = Point(), var end: Point = Point(), var color: Color = Color.WHITE) {

    companion object{
        @JvmStatic
        fun vector(r: Double, theta: Double, start: Point = Point(), color: Color = Color.WHITE) = Vector(start, Point(r * cos(theta), r * sin(theta)) + start, color)
    }

    fun boundingBox(): BoundingBox {
        val a = Point(start.x.coerceAtMost(end.x), start.y.coerceAtMost(end.y))
        val b = Point(start.x.coerceAtLeast(end.x), start.y.coerceAtLeast(end.y))
        return BoundingBox(a, b)
    }

    operator fun plus(that: Vector) = Vector(end = center().end + that.center().end, color = color).center(start)
    operator fun plus(that: Point) = Vector(start + that, end + that, color)

    operator fun minus(that: Vector) = this + -that

    operator fun times(that: Double) = Vector(end = center().end * that, color = color).center(start)
    operator fun times(that: Int) = this * that.toDouble()

    fun scaleFromOrigin(that: Double) = Vector(start * that, end * that, color)

    operator fun div(that: Double) = this * (1.0/that)

    operator fun unaryPlus() = this
    operator fun unaryMinus() = this * -1

    fun x() = center().end.x
    fun y() = center().end.y

    fun center(center: Point = Point()) = Vector(center, end - start + center, color)

    fun angle() = atan2(y(), x())
    fun magnitude() = hypot(x(), y())
    fun norm() = this / magnitude()

    fun comp(): Array<Vector> {
        val x = Vector(Point(start.x, start.y), Point(end.x, start.y), Color.RED)
        val y = Vector(Point(start.x, start.y), Point(start.x, end.y), Color.GREEN)

        return arrayOf(x, y)
    }

    fun dot(that: Vector): Double = x() * that.x() + y() * that.y()
    fun proj(that: Vector) = (that.norm() * (dot(that.norm()))).center(start)
    fun perp(that: Vector) = this - proj(that)
    fun projComp(that: Vector): Array<Vector> {
        val x = proj(that)
        val y = perp(that)
        x.color = Color.RED
        y.color = Color.GREEN

        return arrayOf(x, y)
    }

    fun rotate(theta: Double) = vector(magnitude(), angle() + theta, start, color)

    fun between(a: Point, b: Point): Boolean {
        val ab: Vector = Vector(a, b)
        return if (ab.boundingBox().intersect(boundingBox())) {
            linesIntersect(this, ab)
        } else false
    }

    private fun linesIntersect(v1: Vector, v2: Vector): Boolean {
        val x1 = v1.start.x
        val y1 = v1.start.y
        val x2 = v1.end.x
        val y2 = v1.end.y

        val x3 = v2.start.x
        val y3 = v2.start.y
        val x4 = v2.end.x
        val y4 = v2.end.y

        if (x1 == x2 && y1 == y2 || x3 == x4 && y3 == y4) {
            return false
        }

        val ax = x2 - x1
        val ay = y2 - y1
        val bx = x3 - x4
        val by = y3 - y4
        val cx = x1 - x3
        val cy = y1 - y3

        val alphaNumerator = by * cx - bx * cy
        val commonDenominator = ay * bx - ax * by
        if (commonDenominator > 0) {
            if (alphaNumerator < 0 || alphaNumerator > commonDenominator) {
                return false
            }
        } else if (commonDenominator < 0) {
            if (alphaNumerator > 0 || alphaNumerator < commonDenominator) {
                return false
            }
        }
        val betaNumerator = ax * cy - ay * cx
        if (commonDenominator > 0) {
            if (betaNumerator < 0 || betaNumerator > commonDenominator) {
                return false
            }
        } else if (commonDenominator < 0) {
            if (betaNumerator > 0 || betaNumerator < commonDenominator) {
                return false
            }
        }
        if (commonDenominator == 0.0) {
            val y3LessY1 = y3 - y1
            val collinearityTestForP3 = x1 * (y2 - y3) + x2 * y3LessY1 + x3 * (y1 - y2)
            if (collinearityTestForP3 == 0.0) {
                if (x1 in x3..x4 || x1 in x4..x3 || x2 in x3..x4 || x2 in x4..x3 || x3 in x1..x2 || x3 in x2..x1) {
                    return y1 in y3..y4 || y1 in y4..y3 || y2 in y3..y4 || y2 in y4..y3 || y3 in y1..y2 || y3 in y2..y1
                }
            }
            return false
        }
        return true
    }

    override fun toString(): String = center().end.toString()
    fun toString(inPolar: Boolean): String = if (inPolar) {
            "( r = ${round(magnitude() * 10000) / 10000} , theta = ${round(angle() * 18000/PI)/100}° )"
        } else toString()

}