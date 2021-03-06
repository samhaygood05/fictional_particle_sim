package com.fictional_particle_sim.util

import javax.swing.JFrame
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Rectangle

class VectorSpace : JFrame {

    private val canvas = TheCanvas(Color.BLACK)

    constructor() {
        layout = BorderLayout()
        setSize(Constants.WIDTH, Constants.HEIGHT)
        title = "Vector Space"
        add("Center", canvas)
        defaultCloseOperation = EXIT_ON_CLOSE
        setLocationRelativeTo(null)
        isVisible = true
    }
    constructor(width: Int, height: Int) {
        layout = BorderLayout()
        setSize(width, height)
        title = "Vector Space"
        add("Center", canvas)
        defaultCloseOperation = EXIT_ON_CLOSE
        setLocationRelativeTo(null)
        isVisible = true
    }
}