package com.fictional_particle_sim.physicals

import com.fictional_particle_sim.util.Constants
import com.fictional_particle_sim.geometrics.Point
import com.fictional_particle_sim.geometrics.Vector
import com.fictional_particle_sim.physicals.Shape.*
import com.fictional_particle_sim.util.Constants.Companion.MAX_CHARGE
import java.awt.Color
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.round

class Particle(var pos: Point, var lastPos: Point = pos,
               var vel: Vector = Vector(),
               var mass: Double = 1.0, var charge: Double = 0.0, var maxCharge: Double = 0.0,
               var fixedCharge: Boolean = false, var fixedMaxCharge: Boolean = false, var fixedVel: Boolean = false,
               var chargeColor: Color = Color.GRAY, var maxChargeColor: Color = chargeColor) {


    fun force(b: Particle): Vector {
        val dist = pos.dist(b.pos)
        return if (dist != 0.0) {
            val direct: Vector = Vector(pos, b.pos).norm()
            val distCube = Constants.TWO_PI_SQUARED * Math.pow(dist, 3.0)
            if (dist > 2 * Constants.MIN_DIST) {
                val forceM = charge * b.charge / distCube
                direct * forceM
            } else if (dist > Constants.MIN_DIST) {
                val forceM: Double = (charge * b.charge / 2 - Constants.PUSHBACK_FORCE * (vel.proj(direct) - b.vel.proj(direct)).magnitude()) / distCube
                direct * forceM
            } else if (dist == Constants.MIN_DIST) {
                Vector()
            } else {
                val forceM: Double = Constants.PUSHBACK_FORCE * (vel.proj(direct) - b.vel.proj(direct)).magnitude() / distCube
                direct * forceM
            }
        } else Vector()
    }
    fun acc(force: Vector): Vector = force/mass
    fun applyAcc(acc: Vector) {
        vel += acc
        if (vel.magnitude() > Constants.MAX_VEL) {
            vel = vel.norm() * Constants.MAX_VEL
        }
    }
    fun applyVel(updateLastPos: Boolean) {
        if (updateLastPos) lastPos = pos
        pos += (vel.center() * (Constants.SPF)).end
    }
    fun maxCharge(b: Particle): Double {
        return if (this != b) {
            val dist = pos.dist(b.pos)
            if (dist != 0.0) {
                Constants.CHARGE_FORCE * b.charge / (Constants.TWO_PI_SQUARED * dist.pow(3.0))
            } else b.charge / abs(b.charge)
        } else 0.0
    }
    fun charge(b: Particle): Double {
        return if (this != b) {
            val dist = pos.dist(b.pos)
            if (dist != 0.0) {
                Constants.DELTA_CHARGE_FORCE * b.charge / (2 * Constants.TWO_PI_SQUARED * dist.pow(2.0))
            } else b.charge / abs(b.charge)
        } else 0.0
    }
    fun collide(b: Barrier) {
        when (b.shape) {
            LINE -> {
                if (b.line.between(pos, lastPos)) {
                    pos -= (pos.disp(b.line).center().end * (1.001))
                    vel -= (vel.perp(b.line) * (2.0))
                }
            }
            CIRCLE -> {
                if (this.pos.dist(b.center) <= b.radius && lastPos.dist(b.center) > b.radius) {
                    pos += (Vector(b.center, pos).norm().center() * (b.radius * 1.001 - pos.dist(b.center))).end
                    vel -= (vel.proj(Vector(pos, b.center)) * (2.0))
                } else if (this.pos.dist(b.center) > b.radius && lastPos.dist(b.center) <= b.radius) {
                    pos -= (Vector(pos, b.center).norm().center() * (b.radius * 0.999 - pos.dist(b.center))).end
                    vel -= (vel.proj(Vector(pos, b.center)) * (2.0))
                }
            }
            RECTANGLE -> {
                if (pos.inside(b.boundingBox()) && !lastPos.inside(b.boundingBox())) {
                    when {
                        lastPos.x < b.topLeft.x -> {
                            pos = Point(b.topLeft.x - 0.0001, pos.y)
                            vel = Vector(Point(-vel.center().end.x, vel.center().end.y)) + pos
                        }
                        lastPos.x > b.bottomRight.x -> {
                            pos = Point(b.bottomRight.x + 0.0001, pos.y)
                            vel = Vector(Point(-vel.center().end.x, vel.center().end.y)) + pos
                        }
                        lastPos.y < b.topLeft.y -> {
                            pos = Point(pos.x, b.topLeft.y - 0.0001)
                            vel = Vector(Point(vel.center().end.x, -vel.center().end.y)) + pos
                        }
                        lastPos.y > b.bottomRight.y -> {
                            pos = Point(pos.x, b.bottomRight.y + 0.0001)
                            vel = Vector(Point(vel.center().end.x, -vel.center().end.y)) + pos
                        }
                    }
                } else if (!pos.inside(b.boundingBox()) && lastPos.inside(b.boundingBox())) {
                    when {
                        pos.x < b.topLeft.x -> {
                            pos = Point(b.topLeft.x + 0.0001, pos.y)
                            vel = Vector(Point(-vel.center().end.x, vel.center().end.y)) + pos
                        }
                        pos.x > b.bottomRight.x -> {
                            pos = Point(b.bottomRight.x - 0.0001, pos.y)
                            vel = Vector(Point(-vel.center().end.x, vel.center().end.y)) + pos
                        }
                        pos.y < b.topLeft.y -> {
                            pos = Point(pos.x, b.topLeft.y + 0.0001)
                            vel = Vector(Point(vel.center().end.x, -vel.center().end.y)) + pos
                        }
                        pos.y > b.bottomRight.y -> {
                            pos = Point(pos.x, b.bottomRight.y - 0.0001)
                            vel = Vector(Point(vel.center().end.x, -vel.center().end.y)) + pos
                        }
                    }
                }
            }

        }
    }
    fun collide(f: Field): Boolean {
        return when (f.shape) {
            RECTANGLE -> pos.inside(f.boundingBox())
            CIRCLE -> pos.dist(f.center) <= f.radius
            else -> false
        }
    }

    fun chargeColor() {
        chargeColor = when {
            charge == 0.0 -> Color(128, 128, 128)
            charge >= MAX_CHARGE -> Color(36, 162, 26)
            charge <= -MAX_CHARGE -> Color(229, 43, 43)
            charge > 0 -> Color((36 * charge/MAX_CHARGE + 128 * (1 - charge/MAX_CHARGE)).toInt(), (162 * charge/MAX_CHARGE + 128 * (1 - charge/MAX_CHARGE)).toInt(), (26 * charge/MAX_CHARGE + 128 * (1 - charge/MAX_CHARGE)).toInt())
            else -> Color((229 * -charge/MAX_CHARGE + 128 * (1 + charge/MAX_CHARGE)).toInt(), (43 * -charge/MAX_CHARGE + 128 * (1 + charge/MAX_CHARGE)).toInt(), (43 * -charge/MAX_CHARGE + 128 * (1 + charge/MAX_CHARGE)).toInt())
        }
        if (mass < 0) {
            chargeColor = Color(255 - chargeColor.red, 255 - chargeColor.green, 255 - chargeColor.blue)
        }

        if (abs(maxCharge) > abs(charge)) {
            maxChargeColor = when {
                maxCharge == 0.0 -> Color(128, 128, 128)
                maxCharge >= 1 -> Color(36, 162, 26)
                maxCharge <= -1 -> Color(229, 43, 43)
                maxCharge > 0 -> Color((36 * maxCharge + 128 * (1 - maxCharge)).toInt(), (162 * maxCharge + 128 * (1 - maxCharge)).toInt(), (26 * maxCharge + 128 * (1 - maxCharge)).toInt())
                else -> Color((229 * -maxCharge + 128 * (1 + maxCharge)).toInt(), (43 * -maxCharge + 128 * (1 + maxCharge)).toInt(), (43 * -maxCharge + 128 * (1 + maxCharge)).toInt())
            }
            if (mass < 0) {
                maxChargeColor = Color(255 - maxChargeColor.red, 255 - maxChargeColor.green, 255 - maxChargeColor.blue)
            }
        } else maxChargeColor = chargeColor
    }

    override fun toString(): String {
        return "position: $pos, velocity: ${vel.toString(true)}, Mass:$mass, Charge: ${round(charge * 10000)/10000}, Max Charge: ${round(maxCharge * 10000)/10000}"
    }
}

