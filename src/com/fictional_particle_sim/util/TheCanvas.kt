package com.fictional_particle_sim.util

import com.fictional_particle_sim.Main
import com.fictional_particle_sim.geometrics.Point
import com.fictional_particle_sim.geometrics.Vector
import com.fictional_particle_sim.physicals.Barrier
import com.fictional_particle_sim.physicals.Field
import com.fictional_particle_sim.physicals.Shape
import com.fictional_particle_sim.util.Constants.Companion.GRAPHICS
import com.fictional_particle_sim.util.Graphics.*
import java.awt.Canvas
import java.awt.Color
import java.awt.Graphics
import kotlin.math.roundToInt

class TheCanvas : Canvas() {

    override fun paint(g: Graphics) {
        Main.computeAndDraw(g)
    }

    companion object {
        fun drawVector(g: Graphics, v: Vector, arrow: Boolean, l: Double, h: Double) {
            g.color = v.color
            g.drawLine(v.start.x.roundToInt(), v.start.y.roundToInt(), v.end.x.roundToInt(), v.end.y.roundToInt())
            if (arrow) {
                g.drawLine((v.end.x - h * (v.end.y - v.start.y) - l * (v.end.x - v.start.x)).roundToInt(), (v.end.y + h * (v.end.x - v.start.x) - l * (v.end.y - v.start.y)).roundToInt(), v.end.x.roundToInt(), v.end.y.roundToInt())
                g.drawLine((v.end.x + h * (v.end.y - v.start.y) - l * (v.end.x - v.start.x)).roundToInt(), (v.end.y - h * (v.end.x - v.start.x) - l * (v.end.y - v.start.y)).roundToInt(), v.end.x.roundToInt(), v.end.y.roundToInt())
            }
        }

        fun drawVector(g: Graphics, v: Vector, arrow: Boolean, l: Double, h: Double, color: Color) {
            if (color.alpha != 0){
                g.color = color
                g.drawLine(v.start.x.roundToInt(), v.start.y.roundToInt(), v.end.x.roundToInt(), v.end.y.roundToInt())
                if (arrow) {
                    g.drawLine((v.end.x - h * (v.end.y - v.start.y) - l * (v.end.x - v.start.x)).roundToInt(), (v.end.y + h * (v.end.x - v.start.x) - l * (v.end.y - v.start.y)).roundToInt(), v.end.x.roundToInt(), v.end.y.roundToInt())
                    g.drawLine((v.end.x + h * (v.end.y - v.start.y) - l * (v.end.x - v.start.x)).roundToInt(), (v.end.y - h * (v.end.x - v.start.x) - l * (v.end.y - v.start.y)).roundToInt(), v.end.x.roundToInt(), v.end.y.roundToInt())
                }
            }
        }

        fun drawBarriers(g: Graphics, v: Array<Barrier>) {
            for (barrier in v) {
                if (barrier.color.alpha != 0) {
                    g.color = when (GRAPHICS){
                        FAST -> Color(barrier.color.red * barrier.color.alpha/255, barrier.color.green * barrier.color.alpha/255, barrier.color.blue * barrier.color.alpha/255)
                        FANCY -> barrier.color
                    }

                    when (barrier.shape) {
                        Shape.LINE -> {
                            g.drawLine((barrier.line.start.x * Constants.PPU).roundToInt(), (barrier.line.start.y * Constants.PPU).roundToInt(), (barrier.line.end.x * Constants.PPU).roundToInt(), (barrier.line.end.y * Constants.PPU).roundToInt())
                            g.drawOval(((barrier.center.x - barrier.radius) * Constants.PPU).toInt(), ((barrier.center.y - barrier.radius) * Constants.PPU).toInt(), (2 * barrier.radius * Constants.PPU).toInt(), (2 * barrier.radius * Constants.PPU).toInt())
                            g.drawRect((barrier.topLeft.x * Constants.PPU).toInt(), (barrier.topLeft.y * Constants.PPU).toInt(), ((barrier.bottomRight.x - barrier.topLeft.x) * Constants.PPU).toInt(), ((barrier.bottomRight.y - barrier.topLeft.y) * Constants.PPU).toInt())
                        }
                        Shape.CIRCLE -> {
                            g.drawOval(((barrier.center.x - barrier.radius) * Constants.PPU).toInt(), ((barrier.center.y - barrier.radius) * Constants.PPU).toInt(), (2 * barrier.radius * Constants.PPU).toInt(), (2 * barrier.radius * Constants.PPU).toInt())
                            g.drawRect((barrier.topLeft.x * Constants.PPU).toInt(), (barrier.topLeft.y * Constants.PPU).toInt(), ((barrier.bottomRight.x - barrier.topLeft.x) * Constants.PPU).toInt(), ((barrier.bottomRight.y - barrier.topLeft.y) * Constants.PPU).toInt())
                        }
                        Shape.RECTANGLE -> g.drawRect((barrier.topLeft.x * Constants.PPU).toInt(), (barrier.topLeft.y * Constants.PPU).toInt(), ((barrier.bottomRight.x - barrier.topLeft.x) * Constants.PPU).toInt(), ((barrier.bottomRight.y - barrier.topLeft.y) * Constants.PPU).toInt())
                    }
                }
            }
        }

        fun drawFields(g: Graphics, v: Array<Field>) {
            for (field in v) {
                if (field.color.alpha != 0){
                    g.color = when (GRAPHICS){
                        FAST -> Color(field.color.red * field.color.alpha/255, field.color.green * field.color.alpha/255, field.color.blue * field.color.alpha/255)
                        FANCY -> field.color
                    }
                    when (field.shape) {
                        Shape.CIRCLE -> {
                            g.fillOval(((field.center.x - field.radius) * Constants.PPU).toInt(), ((field.center.y - field.radius) * Constants.PPU).toInt(), (2 * field.radius * Constants.PPU).toInt(), (2 * field.radius * Constants.PPU).toInt())
                            g.fillRect((field.topLeft.x * Constants.PPU).toInt(), (field.topLeft.y * Constants.PPU).toInt(), ((field.bottomRight.x - field.topLeft.x) * Constants.PPU).toInt(), ((field.bottomRight.y - field.topLeft.y) * Constants.PPU).toInt())
                        }
                        Shape.RECTANGLE -> g.fillRect((field.topLeft.x * Constants.PPU).toInt(), (field.topLeft.y * Constants.PPU).toInt(), ((field.bottomRight.x - field.topLeft.x) * Constants.PPU).toInt(), ((field.bottomRight.y - field.topLeft.y) * Constants.PPU).toInt())
                        else -> {}
                    }
                }
            }
        }

        fun drawPoint(g: Graphics, p: Point, r: Int, color: Color) {
            if (color.alpha != 0) {
                g.color = color
                g.fillOval(p.x.roundToInt() - r, p.y.roundToInt() - r, 2 * r, 2 * r)
            }
        }
        fun drawBoxAroundPoint(g: Graphics, p: Point, r: Int, color: Color) {
            if (color.alpha != 0) {
                g.color = color
                g.fillRect(p.x.roundToInt() - r, p.y.roundToInt() - r, 2 * r, 2 * r)
            }
        }
    }
}