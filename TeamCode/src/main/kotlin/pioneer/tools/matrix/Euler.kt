package pioneer.tools.matrix

import kotlin.math.*

data class Euler(val x: Double, val y: Double, val z: Double) : Transform {

    operator fun plus(other: Euler): Euler {
        return Euler(x + other.x, y + other.y, z + other.z)
    }

    operator fun minus(other: Euler): Euler {
        return Euler(x - other.x, y - other.y, z - other.z)
    }

    operator fun times(other: Euler): Euler {
        return Euler(x * other.x, y * other.y, z * other.z)
    }

    operator fun div(other: Euler): Euler {
        return Euler(x / other.x, y / other.y, z / other.z)
    }

    fun inverse(): Euler {
        return Euler(-x, -y, -z)
    }

    fun normalize(): Euler {
        return Euler(x % (2 * PI), y % (2 * PI), z % (2 * PI))
    }

    fun unit(): Euler {
        return Euler(x / PI, y / PI, z / PI)
    }

    // Convert to a quaternion representation
    override fun toQuaternion(): Quaternion {
        val w = cos(x / 2) * cos(y / 2) * cos(z / 2) + sin(x / 2) * sin(y / 2) * sin(z / 2)
        val x = sin(x / 2) * cos(y / 2) * cos(z / 2) - cos(x / 2) * sin(y / 2) * sin(z / 2)
        val y = cos(x / 2) * sin(y / 2) * cos(z / 2) + sin(x / 2) * cos(y / 2) * sin(z / 2)
        val z = cos(x / 2) * cos(y / 2) * sin(z / 2) - sin(x / 2) * sin(y / 2) * cos(z / 2)
        return Quaternion(w, x, y, z)
    }

    // Convert to a Rotation matrix representation
    override fun toRotationMatrix(): Rotation {
        val r11 = cos(y) * cos(z)
        val r12 = cos(y) * sin(z)
        val r13 = -sin(y)
        val r21 = sin(x) * sin(y) * cos(z) - cos(x) * sin(z)
        val r22 = sin(x) * sin(y) * sin(z) + cos(x) * cos(z)
        val r23 = sin(x) * cos(y)
        val r31 = cos(x) * sin(y) * cos(z) + sin(x) * sin(z)
        val r32 = cos(x) * sin(y) * sin(z) - sin(x) * cos(z)
        val r33 = cos(x) * cos(y)
        return Rotation(r11, r12, r13, r21, r22, r23, r31, r32, r33)
    }

    // Convert to an AxisAngle representation
    override fun toAxisAngle(): AxisAngle {
        val angle = sqrt(x * x + y * y + z * z)
        val x = x / angle
        val y = y / angle
        val z = z / angle
        return AxisAngle(angle, Vector(x, y, z))
    }
}