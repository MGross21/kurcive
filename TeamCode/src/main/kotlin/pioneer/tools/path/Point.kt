package pioneer.tools.path

import kotlin.math.sqrt
import pioneer.tools.utils.Angle as Angle

data class Point(
        var x: Double,
        var y: Double,
        var vx: Double = 0.0,
        var vy: Double = 0.0,
        var ax: Double = 0.0,
        var ay: Double = 0.0,
        var jx: Double = 0.0,
        var jy: Double = 0.0
        var heading: Double = 0.0,
        var name: String = ""

){
        // Calculate distance to another point
        fun distanceTo(other: Point): Double =
                hypot(other.x - x, other.y - y)

        // Calculate angle to another point
        fun angleTo(other: Point): Double =
                atan2(other.y - y, other.x - x)

        // Calculate heading difference (considering wrap-around)
        fun headingDifference(other: Point): Double =
                Angle.shortestAngularDistance(heading, other.heading)
}

