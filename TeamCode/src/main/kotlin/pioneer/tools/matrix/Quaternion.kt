package pioneer.tools.matrix

import kotlin.math.*

data class Quaternion(val w: Double, val x: Double, val y: Double, val z: Double) : Transform {

    operator fun plus(other: Quaternion): Quaternion {
        return Quaternion(w + other.w, x + other.x, y + other.y, z + other.z)
    }

    operator fun minus(other: Quaternion): Quaternion {
        return Quaternion(w - other.w, x - other.x, y - other.y, z - other.z)
    }

    operator fun times(other: Quaternion): Quaternion {
        val w = this.w * other.w - this.x * other.x - this.y * other.y - this.z * other.z
        val x = this.w * other.x + this.x * other.w + this.y * other.z - this.z * other.y
        val y = this.w * other.y - this.x * other.z + this.y * other.w + this.z * other.x
        val z = this.w * other.z + this.x * other.y - this.y * other.x + this.z * other.w
        return Quaternion(w, x, y, z)
    }

    operator fun div(other: Quaternion): Quaternion {
        return this * other.inverse()
    }

    // Convert to a Rotation matrix representation
    override fun toRotationMatrix(): Rotation {
        val r11 = 1 - 2 * (y * y + z * z)
        val r12 = 2 * (x * y - z * w)
        val r13 = 2 * (x * z + y * w)
        val r21 = 2 * (x * y + z * w)
        val r22 = 1 - 2 * (x * x + z * z)
        val r23 = 2 * (y * z - x * w)
        val r31 = 2 * (x * z - y * w)
        val r32 = 2 * (y * z + x * w)
        val r33 = 1 - 2 * (x * x + y * y)
        return Rotation(r11, r12, r13, r21, r22, r23, r31, r32, r33)
    }

    // Convert to an Euler representation
    override fun toEuler(): Euler {
        val x = atan2(2 * (w * x + y * z), 1 - 2 * (x * x + y * y))
        val y = asin(2 * (w * y - z * x))
        val z = atan2(2 * (w * z + x * y), 1 - 2 * (y * y + z * z))
        return Euler(x, y, z)
    }

    // Convert to an AxisAngle representation
    override fun toAxisAngle(): AxisAngle {
        val angle = 2 * acos(w)
        val x = x / sin(angle / 2)
        val y = y / sin(angle / 2)
        val z = z / sin(angle / 2)
        return AxisAngle(angle, Vector(x, y, z))
    }
}