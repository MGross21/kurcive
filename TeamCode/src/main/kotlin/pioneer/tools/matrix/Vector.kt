package pioneer.tools.matrix

data class Vector(val x: Double, val y: Double, val z: Double) {

    // Dot product of two vectors
    infix fun dot(other: Vector): Double {
        return this.x * other.x + this.y * other.y + this.z * other.z
    }

    // Cross product of two vectors
    infix fun cross(other: Vector): Vector {
        return Vector(
            this.y * other.z - this.z * other.y,
            this.z * other.x - this.x * other.z,
            this.x * other.y - this.y * other.x
        )
    }

    // Overloads * operator for scalar multiplication
    operator fun times(scalar: Double): Vector {
        return Vector(x * scalar, y * scalar, z * scalar)
    }
}
