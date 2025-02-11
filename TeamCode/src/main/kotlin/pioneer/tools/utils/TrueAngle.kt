package pioneer.tools.utils

import kotlin.math.PI

class TrueAngle(
    initialAngle: Double,
    private val minRange: Double = -PI,
    private val maxRange: Double = PI
) {
    private var currentAngle: Double = initialAngle
    private var prevAngle: Double = initialAngle
    private val range: Double = maxRange - minRange

    init {
        require(range > 0) { "Range must be positive" }
    }

    fun update(inputAngle: Double): Double {
        val deltaAngle = calculateDelta(inputAngle, prevAngle)
        currentAngle += deltaAngle
        prevAngle = inputAngle
        return currentAngle
    }

    fun reset(newAngle: Double) {
        currentAngle = newAngle
        prevAngle = newAngle
    }

    val angle: Double
        get() = currentAngle

    private fun calculateDelta(newAngle: Double, oldAngle: Double): Double {
        return when {
            (newAngle - oldAngle) > (range / 2) -> (newAngle - oldAngle - range)
            (newAngle - oldAngle) < (-range / 2) -> (newAngle - oldAngle + range)
            else -> (newAngle - oldAngle)
        }
    }
}