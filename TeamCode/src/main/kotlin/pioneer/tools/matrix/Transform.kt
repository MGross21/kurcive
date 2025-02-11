package pioneer.tools.matrix

interface Transform {

    // Convert to a quaternion representation
    fun toQuaternion(): Quaternion

    // Convert to an Euler representation
    fun toEuler(): Euler

    // Convert to a Rotation matrix representation
    fun toRotationMatrix(): Rotation

    // Convert to an AxisAngle representation
    fun toAxisAngle(): AxisAngle

    // Optional: Add any other shared methods for transformations
}
