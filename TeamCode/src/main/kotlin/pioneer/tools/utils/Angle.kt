package pioneer.tools.utils

import kotlin.math.*

/**
 * Utility object for angle-related calculations and conversions.
 */
object Angle {
    private const val TWO_PI = 2.0 * PI
    private const val RAD_TO_DEG = 180.0 / PI
    private const val DEG_TO_RAD = PI / 180.0

    /**
     * Normalize an angle in radians to the range [-π, π).
     */
    fun wrapToPI(radians: Double): Double =
        (radians + PI).rem(TWO_PI).let { if (it < 0) it + TWO_PI else it } - PI

    /**
     * Normalize an angle in degrees to the range [-180, 180).
     */
    fun wrapTo180(degrees: Double): Double =
        (degrees + 180.0).rem(360.0).let { if (it < 0) it + 360.0 else it } - 180.0

    /**
     * Normalize an angle in degrees to the range [0, 360).
     */
    fun wrapTo360(degrees: Double): Double =
        degrees.rem(360.0).let { if (it < 0) it + 360.0 else it }

    /**
     * Calculate the shortest angular distance between two angles in radians.
     */
    fun shortestAngularDistance(from: Double, to: Double): Double =
        wrapToPI(to - from)

    /**
     * Convert radians to degrees.
     */
    fun toDegrees(radians: Double): Double = radians * RAD_TO_DEG

    /**
     * Convert degrees to radians.
     */
    fun toRadians(degrees: Double): Double = degrees * DEG_TO_RAD

    /**
     * Interpolate between two angles (in radians) by a factor t.
     */
    fun interpolate(start: Double, end: Double, t: Double): Double {
        val diff = shortestAngularDistance(start, end)
        return wrapToPI(start + diff * t)
    }

    /**
     * Calculate the average of a list of angles (in radians).
     */
    fun averageAngles(angles: List<Double>): Double {
        val x = angles.map { cos(it) }.average()
        val y = angles.map { sin(it) }.average()
        return atan2(y, x)
    }

    /**
     * Check if an angle is between two other angles (all in radians).
     */
    fun isAngleBetween(angle: Double, start: Double, end: Double): Boolean {
        val normAngle = wrapToPI(angle - start)
        val normEnd = wrapToPI(end - start)
        return if (normEnd >= 0) {
            normAngle in 0.0..normEnd
        } else {
            normAngle <= 0 || normAngle >= normEnd + TWO_PI
        }
    }

    /**
     * Calculate the heading given x and y coordinates.
     */
    fun calculateHeading(x: Double, y: Double): Double = atan2(y, x)

    /**
     * Convert polar coordinates to Cartesian coordinates.
     */
    fun polarToCartesian(r: Double, theta: Double): Pair<Double, Double> =
        Pair(r * cos(theta), r * sin(theta))

    /**
     * Convert Cartesian coordinates to polar coordinates.
     */
    fun cartesianToPolar(x: Double, y: Double): Pair<Double, Double> =
        Pair(sqrt(x*x + y*y), atan2(y, x))
}
