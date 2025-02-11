package robotics.kinematics

import kotlin.math.acos
import kotlin.math.atan2
import kotlin.math.sqrt

object InverseKinematics {
    // 2D Inverse Kinematics
    fun calculate(x: Double, y: Double, l1: Double, l2: Double): Pair<Double, Double>? {
        val d = (x * x + y * y - l1 * l1 - l2 * l2) / (2 * l1 * l2)
        if (d < -1 || d > 1) return null // No solution exists
        val theta2 = acos(d)
        val theta1 = atan2(y, x) - atan2(l2 * sin(theta2), l1 + l2 * cos(theta2))
        return Pair(theta1, theta2)
    }
}
