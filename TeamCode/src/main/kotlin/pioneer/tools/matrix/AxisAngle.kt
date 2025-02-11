package pioneer.tools.matrix

import kotlin.math.*

data class AxisAngle(val angle: Double, val axis: Vector) : Transform {

    operator fun plus(other: AxisAngle): AxisAngle {
        return AxisAngle(angle + other.angle, axis + other.axis)
    }

    operator fun minus(other: AxisAngle): AxisAngle {
        return AxisAngle(angle - other.angle, axis - other.axis)
    }

    operator fun times(other: AxisAngle): AxisAngle {
        val angle = this.angle * other.angle
        val axis = this.axis cross other.axis
        return AxisAngle(angle, axis)
    }

    operator fun div(other: AxisAngle): AxisAngle {
        return this * other.inverse()
    }

    fun inverse(): AxisAngle {
        return AxisAngle(-angle, -axis)
    }

    fun normalize(): AxisAngle {
        return AxisAngle(angle % (2 * PI), axis)
    }

    fun unit(): AxisAngle {
        return AxisAngle(angle / PI, axis)
    }

    // Convert to a quaternion representation
    override fun toQuaternion(): Quaternion {
        val w = cos(angle / 2)
        val x = axis.x * sin(angle / 2)
        val y = axis.y * sin(angle / 2)
        val z = axis.z * sin(angle / 2)
        return Quaternion(w, x, y, z)
    }

    // Convert to an Euler representation
    override fun toEuler(): Euler {
        val x = atan2(2 * (w * x + y * z), 1 - 2 * (x * x + y * y))
        val y = asin(2 * (w * y - z * x))
        val z = atan2(2 * (w * z + x * y), 1 - 2 * (y * y + z * z))
        return Euler(x, y, z)
    }

    // Convert to a Rotation matrix representation
    override fun toRotationMatrix(): Rotation {
        val c = cos(angle)
        val s = sin(angle)
        val t = 1 - c
        val x = axis.x
        val y = axis.y
        val z = axis.z
        val r11 = c + x * x * t
        val r12 = x * y * t - z * s
        val r13 = x * z * t + y * s
        val r21 = y * x * t + z * s
        val r22 = c + y * y * t
        val r23 = y * z * t - x * s
        val r31 = z * x * t - y * s
        val r32 = z * y * t + x * s
        val r33 = c + z * z * t
        return Rotation(r11, r12, r13, r21, r22, r23, r31, r32, r33)
    }
}


