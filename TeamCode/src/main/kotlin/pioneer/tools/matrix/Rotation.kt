package pioneer.tools.matrix

import org.apache.commons.math3.linear.Array2DRowRealMatrix
import org.apache.commons.math3.linear.EigenDecomposition
import kotlin.math.*


data class Rotation(
    val r11: Double, val r12: Double, val r13: Double,
    val r21: Double, val r22: Double, val r23: Double,
    val r31: Double, val r32: Double, val r33: Double
) : Transform {


    operator fun plus(other: Rotation): Rotation {
        return Rotation(
            r11 + other.r11, r12 + other.r12, r13 + other.r13,
            r21 + other.r21, r22 + other.r22, r23 + other.r23,
            r31 + other.r31, r32 + other.r32, r33 + other.r33
        )
    }

    operator fun minus(other: Rotation): Rotation {
        return Rotation(
            r11 - other.r11, r12 - other.r12, r13 - other.r13,
            r21 - other.r21, r22 - other.r22, r23 - other.r23,
            r31 - other.r31, r32 - other.r32, r33 - other.r33
        )
    }

    operator fun times(other: Rotation): Rotation {
        val r11 = this.r11 * other.r11 + this.r12 * other.r21 + this.r13 * other.r31
        val r12 = this.r11 * other.r12 + this.r12 * other.r22 + this.r13 * other.r32
        val r13 = this.r11 * other.r13 + this.r12 * other.r23 + this.r13 * other.r33
        val r21 = this.r21 * other.r11 + this.r22 * other.r21 + this.r23 * other.r31
        val r22 = this.r21 * other.r12 + this.r22 * other.r22 + this.r23 * other.r32
        val r23 = this.r21 * other.r13 + this.r22 * other.r23 + this.r23 * other.r33
        val r31 = this.r31 * other.r11 + this.r32 * other.r21 + this.r33 * other.r31
        val r32 = this.r31 * other.r12 + this.r32 * other.r22 + this.r33 * other.r32
        val r33 = this.r31 * other.r13 + this.r32 * other.r23 + this.r33 * other.r33
        return Rotation(r11, r12, r13, r21, r22, r23, r31, r32, r33)
    }

    operator fun div(other: Rotation): Rotation {
        return this * other.inverse()
    }

    fun inverse(): Rotation {
        return Rotation(r11, r21, r31, r12, r22, r32, r13, r23, r33)
    }

    // Compute eigenvalues and eigenvectors using Apache Commons Math
    fun eigenvalues(): DoubleArray {
        val matrix = Array2DRowRealMatrix(
            arrayOf(
                doubleArrayOf(r11, r12, r13),
                doubleArrayOf(r21, r22, r23),
                doubleArrayOf(r31, r32, r33)
            )
        )

        val eigenDecomposition = EigenDecomposition(matrix)
        return eigenDecomposition.realEigenvalues
    }

    fun eigenvectors(): ArrayList<DoubleArray> {
        val matrix = Array2DRowRealMatrix(
            arrayOf(
                doubleArrayOf(r11, r12, r13),
                doubleArrayOf(r21, r22, r23),
                doubleArrayOf(r31, r32, r33)
            )
        )

        val eigenDecomposition = EigenDecomposition(matrix)
        val eigenVectors = ArrayList<DoubleArray>()

        // Eigenvectors are stored as columns in the matrix
        for (i in 0 until matrix.columnDimension) {
            eigenVectors.add(eigenDecomposition.getEigenvector(i).toArray())
        }

        return eigenVectors
    }

    // Convert to a quaternion representation
    override fun toQuaternion(): Quaternion {
        val w = 0.5 * sqrt(1 + r11 + r22 + r33)
        val x = 0.5 * sqrt(1 + r11 - r22 - r33)
        val y = 0.5 * sqrt(1 - r11 + r22 - r33)
        val z = 0.5 * sqrt(1 - r11 - r22 + r33)
        return Quaternion(w, x, y, z)
    }

    // Convert to an Euler representation
    override fun toEuler(): Euler {
        val x = atan2(r32, r33)
        val y = atan2(-r31, sqrt(r32 * r32 + r33 * r33))
        val z = atan2(r21, r11)
        return Euler(x, y, z)
    }

    // Convert to an AxisAngle representation
    override fun toAxisAngle(): AxisAngle {
        val angle = acos((r11 + r22 + r33 - 1) / 2)
        val x = (r32 - r23) / (2 * sin(angle))
        val y = (r13 - r31) / (2 * sin(angle))
        val z = (r21 - r12) / (2 * sin(angle))
        return AxisAngle(angle, Vector(x, y, z))
    }
}
