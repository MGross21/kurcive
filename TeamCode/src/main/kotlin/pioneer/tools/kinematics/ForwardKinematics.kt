package pioneer.tools.kinematics

import kotlin.math.cos
import kotlin.math.sin


object ForwardKinematics {
    // 2D Forward Kinematics
    fun calculate(theta1: Double, theta2: Double, l1: Double, l2: Double): Pair<Double, Double> {
        val x = l1 * cos(theta1) + l2 * cos(theta1 + theta2)
        val y = l1 * sin(theta1) + l2 * sin(theta1 + theta2)
        return Pair(x, y)
    }
}