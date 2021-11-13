package com.fictional_particle_sim.util

import com.fictional_particle_sim.Main
import com.fictional_particle_sim.geometrics.Point
import com.fictional_particle_sim.geometrics.Vector
import com.fictional_particle_sim.physicals.Barrier
import com.fictional_particle_sim.physicals.Field
import com.fictional_particle_sim.physicals.Shape
import java.awt.Canvas
import java.awt.Color
import java.awt.Graphics
import kotlin.math.roundToInt

class TheCanvas : Canvas() {
    override fun paint(g: Graphics) {
        Main.computeAndDraw(g, this)
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

        fun drawVector(g: Graphics, v: Vector, arrow: Boolean, l: Double, h: Double, color: Color?) {
            g.color = color
            g.drawLine(v.start.x.roundToInt(), v.start.y.roundToInt(), v.end.x.roundToInt(), v.end.y.roundToInt())
            if (arrow) {
                g.drawLine((v.end.x - h * (v.end.y - v.start.y) - l * (v.end.x - v.start.x)).roundToInt(), (v.end.y + h * (v.end.x - v.start.x) - l * (v.end.y - v.start.y)).roundToInt(), v.end.x.roundToInt(), v.end.y.roundToInt())
                g.drawLine((v.end.x + h * (v.end.y - v.start.y) - l * (v.end.x - v.start.x)).roundToInt(), (v.end.y - h * (v.end.x - v.start.x) - l * (v.end.y - v.start.y)).roundToInt(), v.end.x.roundToInt(), v.end.y.roundToInt())
            }
        }

        fun drawVectors(g: Graphics, v: Array<Vector>, arrow: Boolean, l: Double, h: Double) {
            for (vector in v) {
                drawVector(g, vector, arrow, l, h)
            }
        }

        fun drawBarriers(g: Graphics, v: Array<Barrier>) {
            for (barrier in v) {
                g.color = barrier.color
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

        fun drawFields(g: Graphics, v: Array<Field>) {
            for (field in v) {
                g.color = field.color
                when (field.shape) {
                    Shape.CIRCLE -> {
                        g.fillOval(((field.center.x - field.radius) * Constants.PPU).toInt(), ((field.center.y - field.radius) * Constants.PPU).toInt(), (2 * field.radius * Constants.PPU).toInt(), (2 * field.radius * Constants.PPU).toInt())
                        g.fillRect((field.topLeft.x * Constants.PPU).toInt(), (field.topLeft.y * Constants.PPU).toInt(), ((field.bottomRight.x - field.topLeft.x) * Constants.PPU).toInt(), ((field.bottomRight.y - field.topLeft.y) * Constants.PPU).toInt())
                    }
                    Shape.RECTANGLE -> g.fillRect((field.topLeft.x * Constants.PPU).toInt(), (field.topLeft.y * Constants.PPU).toInt(), ((field.bottomRight.x - field.topLeft.x) * Constants.PPU).toInt(), ((field.bottomRight.y - field.topLeft.y) * Constants.PPU).toInt())
                }
            }
        }

        fun drawPoint(g: Graphics, p: Point, r: Int, color: Color?) {
            g.color = color
            g.fillOval(p.x.roundToInt() - r, p.y.roundToInt() - r, 2 * r, 2 * r)
        }

        fun drawPoints(g: Graphics, p: Array<Point>, r: Int, color: Color?) {
            for (point in p) {
                drawPoint(g, point, r, color)
            }
        }
    }
}