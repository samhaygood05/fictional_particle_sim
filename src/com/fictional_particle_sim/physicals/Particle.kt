package com.fictional_particle_sim.physicals

import com.fictional_particle_sim.util.Constants
import com.fictional_particle_sim.geometrics.Point
import com.fictional_particle_sim.geometrics.Vector
import com.fictional_particle_sim.physicals.Shape.*
import com.fictional_particle_sim.physicals.Type.*
import com.fictional_particle_sim.util.Constants.Companion.MAX_ATTRACTOR_CHARGE
import com.fictional_particle_sim.util.Constants.Companion.MAX_CHARGE
import java.awt.Color
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sign

class Particle(var pos: Point, var lastPos: Point = pos,
               var vel: Vector = Vector(),
               var mass: Double = 1.0, var charge: Double = 0.0, var targetCharge: Double = 0.0,
               var fixedCharge: Boolean = false, var fixedMaxCharge: Boolean = false, var fixedVel: Boolean = false, var type: Type = CHARGED,
               var chargeColor: Color = Color.GRAY, var targetChargeColor: Color = chargeColor) {


    fun force(b: Particle): Vector {
        val dist = pos.dist(b.pos)
        val tempCharge = when (type){
            UNIVERSAL_ATTRACTOR -> when (b.type) {
                UNIVERSAL_ATTRACTOR -> -MAX_ATTRACTOR_CHARGE
                CHARGED, UNIVERSAL_REPELLER -> MAX_ATTRACTOR_CHARGE
            }
            UNIVERSAL_REPELLER -> when (b.type) {
                UNIVERSAL_ATTRACTOR -> MAX_ATTRACTOR_CHARGE
                CHARGED, UNIVERSAL_REPELLER -> -MAX_ATTRACTOR_CHARGE
            }
            CHARGED -> when (b.type) {
                UNIVERSAL_ATTRACTOR, UNIVERSAL_REPELLER -> 1.0
                CHARGED -> charge
            }
        }
        val tempChargeB = when (b.type) {
            UNIVERSAL_ATTRACTOR, UNIVERSAL_REPELLER -> MAX_ATTRACTOR_CHARGE
            CHARGED -> when (type) {
                UNIVERSAL_ATTRACTOR, UNIVERSAL_REPELLER -> 1.0
                CHARGED -> b.charge
            }
        }
        return if (dist != 0.0) {
            val direct: Vector = Vector(pos, b.pos).norm()
            val distCube = Constants.TWO_PI_SQUARED * dist.pow(3.0)
            when {
                dist > 2 * Constants.MIN_DIST -> {
                    val forceM = tempCharge * tempChargeB / distCube
                    direct * forceM
                }
                dist > Constants.MIN_DIST -> {
                    val forceM: Double = (tempCharge * tempChargeB / 2 - Constants.PUSHBACK_FORCE * (vel.proj(direct) - b.vel.proj(direct)).magnitude()) / distCube
                    direct * forceM
                }
                dist == Constants.MIN_DIST -> {
                    val forceM = tempCharge * tempChargeB / distCube
                    if (forceM > 0) Vector() else direct * forceM
                }
                else -> {
                    var forceM = tempCharge * tempChargeB / distCube
                    if (forceM >= 0) {
                        forceM = Constants.PUSHBACK_FORCE * (vel.proj(direct) - b.vel.proj(direct)).magnitude() / distCube
                    }
                    direct * forceM
                }
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
        return if (this != b && (b.type != UNIVERSAL_ATTRACTOR || b.type != UNIVERSAL_ATTRACTOR)) {
            val dist = pos.dist(b.pos)
            if (dist != 0.0) {
                Constants.CHARGE_FORCE * b.charge / (Constants.TWO_PI_SQUARED * dist.pow(3.0))
            } else sign(b.charge) * MAX_CHARGE
        } else 0.0
    }
    fun charge(b: Particle): Double {
        return if (this != b && (b.type != UNIVERSAL_ATTRACTOR || b.type != UNIVERSAL_ATTRACTOR)) {
            val dist = pos.dist(b.pos)
            if (dist != 0.0) {
                Constants.DELTA_CHARGE_FORCE * b.charge / (2 * Constants.TWO_PI_SQUARED * dist.pow(2.0))
            } else sign(b.charge) * MAX_CHARGE
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
        val gray = when (type) {
            CHARGED -> Color(128, 128, 128)
            UNIVERSAL_ATTRACTOR -> Color(252, 211, 3)
            UNIVERSAL_REPELLER -> Color(3, 44, 252)
        }
        val red = when (type) {
            CHARGED -> Color(36, 162, 26)
            UNIVERSAL_ATTRACTOR -> Color(252, 211, 3)
            UNIVERSAL_REPELLER -> Color(3, 44, 252)
        }
        val green = when (type) {
            CHARGED -> Color(229, 43, 43)
            UNIVERSAL_ATTRACTOR -> Color(252, 211, 3)
            UNIVERSAL_REPELLER -> Color(3, 44, 252)
        }
        val tempMaxCharge = when (type) {
            UNIVERSAL_ATTRACTOR, UNIVERSAL_REPELLER -> MAX_ATTRACTOR_CHARGE
            CHARGED -> MAX_CHARGE
        }

        chargeColor = when {
            charge == 0.0 -> gray
            charge >= tempMaxCharge -> red
            charge <= -tempMaxCharge -> green
            charge > 0 -> Color((red.red * charge/tempMaxCharge + gray.red * (1 - charge/tempMaxCharge)).toInt(), (red.green * charge/tempMaxCharge + gray.green * (1 - charge/tempMaxCharge)).toInt(), (red.blue * charge/tempMaxCharge + gray.blue * (1 - charge/tempMaxCharge)).toInt())
            else -> Color((green.red * -charge/tempMaxCharge + gray.red * (1 + charge/tempMaxCharge)).toInt(), (green.green * -charge/tempMaxCharge + gray.green * (1 + charge/tempMaxCharge)).toInt(), (green.blue * -charge/tempMaxCharge + gray.blue * (1 + charge/tempMaxCharge)).toInt())
        }
        if (mass < 0) {
            chargeColor = Color(255 - chargeColor.red, 255 - chargeColor.green, 255 - chargeColor.blue)
        }

        if (abs(targetCharge) > abs(charge)) {
            targetChargeColor = when {
                targetCharge == 0.0 -> gray
                targetCharge >= tempMaxCharge -> red
                targetCharge <= -tempMaxCharge -> green
                targetCharge > 0 -> Color((red.red * targetCharge/tempMaxCharge + gray.red * (1 - targetCharge/tempMaxCharge)).toInt(), (red.green * targetCharge/tempMaxCharge + gray.green * (1 - targetCharge/tempMaxCharge)).toInt(), (red.blue * targetCharge/tempMaxCharge + gray.blue * (1 - targetCharge/tempMaxCharge)).toInt())
                else -> Color((green.red * -targetCharge/tempMaxCharge + gray.red * (1 + targetCharge/tempMaxCharge)).toInt(), (green.green * -targetCharge/tempMaxCharge + gray.green * (1 + targetCharge/tempMaxCharge)).toInt(), (green.blue * -targetCharge/tempMaxCharge + gray.blue * (1 + targetCharge/tempMaxCharge)).toInt())
            }
            if (mass < 0) {
                targetChargeColor = Color(255 - targetChargeColor.red, 255 - targetChargeColor.green, 255 - targetChargeColor.blue)
            }
        } else targetChargeColor = chargeColor
    }

    override fun toString(): String {
        return "position: $pos, velocity: ${vel.toString(true)}, Mass:$mass, Charge: ${round(charge * 10000)/10000}, Max Charge: ${round(targetCharge * 10000)/10000}"
    }
}

enum class Type {
    CHARGED, UNIVERSAL_ATTRACTOR, UNIVERSAL_REPELLER
}

